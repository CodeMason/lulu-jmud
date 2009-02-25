package jmud.engine.behavior.definitions;

import jmud.engine.behavior.BehaviorType;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.definitions.RunEventJob;
import jmud.engine.object.JMudObject;
import jmud.engine.object.JMudObjectUtils;

/**
 *
 * @author david.h.loman
 */
public class GetBehavior extends AbstractBehavior {
	
	public GetBehavior() {
		this.behaviorType = BehaviorType.Get;
	}

	@Override
	public final boolean targetBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		JMudObject source = jme.getSource();
		JMudObject target = jme.getTarget();

		//Remove the target's parent
		JMudObjectUtils.changeParent(source, target);
		
		// prep the 'response' JMudEvent
		JMudEvent responseJme = new JMudEvent(JMudEventType.Got, target, source, jme.getAffRange());
		RunEventJob rej =  new RunEventJob(responseJme);
		rej.selfSubmit();

		return true;
	}

	@Override
	protected boolean bystanderBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		return this.ignoreEvent();
	}

	@Override
	protected boolean sourceBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		// loop back to bystanderBehavior()
		return this.bystanderBehavior(whoToRunThisBehaviorOn, jme);
	}
}
