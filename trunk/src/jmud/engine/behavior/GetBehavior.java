package jmud.engine.behavior;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 *
 *
 * @author david.h.loman
 */
public class GetBehavior extends AbstractBehavior {

	/**
	 * Default constructor.
	 */
	public GetBehavior(JMudObject owner) {
		super(owner);
		// Register a Behavior Object of this type to respond to a
		// EventType.Get
		this.eventTypesHandled.add(JMudEventType.Get);
	}

	/**
	 * @see jmud.engine.behavior.AbstractBehavior#behave()
	 * @return true
	 */

	@Override
	public final boolean targetBehavior() {
		JMudObject source = this.event.getSource();
		JMudObject target = this.event.getTarget();

		//Remove the target's parent
		target.setParentObject(null);
		
		source.addChild(target);
	
		// prep the 'response' JMudEvent
		JMudEvent jme = new JMudEvent(JMudEventType.Got, target, source);

		jme.selfSubmit();

		return true;
	}

	@Override
	protected boolean bystanderBehavior() {
		// If I get a GetEvent, and I am not the target... I dont care! Ignore!
		return true;
	}

	@Override
	protected boolean sourceBehavior() {
		// loop back to ccBehavior()
		return this.bystanderBehavior();
	}
}
