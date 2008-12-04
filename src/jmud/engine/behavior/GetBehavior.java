package jmud.engine.behavior;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 *
 *
 * @author david.h.loman
 */
public class GetBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public GetBehavior(JMudObject owner) {
		super(owner);
		// Register a Behavior Object of this type to respond to a
		// EventType.GetEvent
		this.eventTypesHandled.add(JMudEventType.GetEvent);
	}

	/**
	 * @see jmud.engine.behavior.Behavior#behave()
	 * @return true
	 */

	@Override
	public final boolean targetBehavior() {
		JMudObject source = this.event.getSource();
		JMudObject target = this.event.getTarget();

        // ToDo CM: I don't think I could be trusted to do this on my own every time :)
        target.orphan();
		source.childrenAdd(target);

		// prep the 'response' JMudEvent
		JMudEvent jme = new JMudEvent(JMudEventType.GotEvent, target, source);

		jme.submitSelf();

		return true;
	}

	@Override
	protected boolean ccBehavior() {
		// If I get a GetEvent, and I am not the target... I dont care! Ignore!
		return true;
	}

	@Override
	protected boolean sourceBehavior() {
		// loop back to ccBehavior()
		return this.ccBehavior();
	}

	/**
	 * @see jmud.engine.behavior.Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
	@Override
	public final Behavior clone() {
		return new GetBehavior(this.owner);
	}

}
