package jmud.engine.behavior.definitions;

import jmud.engine.behavior.BehaviorType;
import jmud.engine.event.JMudEvent;
import jmud.engine.object.JMudObject;

/**
 * 
 * @author david.h.loman
 */
public class GotBehavior extends AbstractBehavior {

	public GotBehavior() {
		this.behaviorType = BehaviorType.Got;
	}

	/**
	 * Anything the object that did the 'get' does
	 * 
	 * @return
	 */
	@Override
	public final boolean targetBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		jme.getTarget().sendTextToObject(this.getMessageToTarget(whoToRunThisBehaviorOn, jme));
		return true;
	}

	/**
	 * Anything a non-participant does
	 * 
	 * @return
	 */
	@Override
	protected boolean bystanderBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		whoToRunThisBehaviorOn.sendTextToObject(this.getMessageToBystander(whoToRunThisBehaviorOn, jme));
		return true;
	}

	/**
	 * Anything the "gotten" object does
	 * 
	 * @return
	 */
	@Override
	protected boolean sourceBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		jme.getSource().sendTextToObject(this.getMessageToSource(whoToRunThisBehaviorOn, jme));
		return true;
	}

	@Override
	protected String getMessageToTarget(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		return "You get the " + jme.getSource().getDisplayedName() + ".";
	}

	@Override
	protected String getMessageToBystander(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		return jme.getTarget().getDisplayedName() + " gets the " + jme.getSource().getDisplayedName() + ".";
	}

	@Override
	protected String getMessageToSource(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		return jme.getTarget().getDisplayedName() + " picks YOU up!!";
	}

}
