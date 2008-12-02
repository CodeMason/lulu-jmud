package jmud.engine.behavior;

import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 * 
 * 
 * @author david.h.loman
 */
public class GotBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public GotBehavior(JMudObject owner) {
		super(owner);
		// Register a Behavior Object of this type to respond to a
		// EventType.GetEvent
		this.eventTypesHandled.add(JMudEventType.GotEvent);
	}

	/**
	 * @see jmud.engine.behavior.Behavior#behave()
	 * @return true
	 */

	@Override
	public final boolean targetBehavior() {
		// What the owner sees
		synchronized (System.out) {
			System.out.println(this.owner.getName() + " sees: ");
			System.out.println("\tYou get the " + this.event.getSource().getName());
		}

		return true;
	}

	@Override
	protected boolean ccBehavior() {
		// What anyone else sees.
		synchronized (System.out) {
			System.out.println(this.owner.getName() + " sees: ");
			System.out.println("\t" + this.event.getTarget().getName() + " gets the "
					+ this.event.getSource().getName());
		}

		return true;
	}

	@Override
	protected boolean sourceBehavior() {
		// What You else sees.
		synchronized (System.out) {
			System.out.println(this.owner.getName() + " sees: ");
			System.out.println("\t" + this.event.getTarget().getName() + " picks YOU up!!");
		}

		return true;
	}


	
	/**
	 * @see jmud.engine.behavior.Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
	@Override
	public final Behavior clone() {
		synchronized (System.out) {
			System.out.println("GotBehavior.clone()");
		}
		return new GotBehavior(this.owner);
	}
}
