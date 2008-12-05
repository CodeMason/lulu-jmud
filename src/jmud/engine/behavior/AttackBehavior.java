package jmud.engine.behavior;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 * Added this to try out my event trigger mechanism
 *
 * @author Chris Maguire
 */
public class AttackBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public AttackBehavior(JMudObject owner) {
		super(owner);
		// Register a Behavior Object of this type to respond to a
		// EventType.Get
		this.eventTypesHandled.add(JMudEventType.Attack);
	}

	/**
     * Just print out that we got here for now
     *
	 * @see Behavior#behave()
	 * @return true
	 */
	@Override
	public final boolean targetBehavior() {
		// prep the 'response' JMudEvent
		JMudEvent jme = new JMudEvent(JMudEventType.Attacked, this.event.getTarget(), this.event.getSource());

		jme.submitSelf();

		return true;
	}

	@Override
	protected boolean ccBehavior() {
		// If I get a AttackEvent, and I am not the target... I dont care! Ignore!
		return true;
	}

	@Override
	protected boolean sourceBehavior() {
		// If I get a AttackEvent, and I am not the target... I dont care! Ignore!
        return true;
    }

	/**
	 * @see Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
	@Override
	public final Behavior clone() {
		return new AttackBehavior(this.owner);
	}

}