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
		// EventType.GetEvent
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
		JMudObject source = this.event.getSource();
		JMudObject target = this.event.getTarget();

		synchronized (System.out) {
			System.out.println("(" + this.getID() + ") AttackBehavior.behave(): " + this.event.toString());
		}

		// prep the 'response' JMudEvent
		JMudEvent jme = new JMudEvent(JMudEventType.Attacked, target, source);

		synchronized (System.out) {
			System.out.println("(" + this.getID() + ") AttackBehavior.behave() 'response': " + jme.toString());
		}
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
        JMudObject source = this.event.getSource();
        JMudObject target = this.event.getTarget();
        
        synchronized(System.out){
            System.out.println("(" + this.getID() + ") AttackBehavior.behave(): " + this.event.toString());
        }

        // prep the 'response' JMudEvent
        JMudEvent jme = new JMudEvent(JMudEventType.Attacked, target, source);

        synchronized(System.out){
            System.out.println("(" + this.getID() + ") AttackBehavior.behave() 'response': " + jme.toString());
        }
        jme.submitSelf();

        return true;
    }

	/**
	 * @see Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
	@Override
	public final Behavior clone() {
		synchronized (System.out) {
			System.out.println("AttackBehavior.clone()");
		}

		return new AttackBehavior(this.owner);
	}

}