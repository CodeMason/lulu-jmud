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
	public GetBehavior() {
		super();
		// Register a Behavior Object of this type to respond to a
		// EventType.GetEvent
		this.eventTypesHandled.add(JMudEventType.GetEvent);
	}

	/**
	 * @see jmud.engine.behavior.Behavior#behave()
	 * @return true
	 */

	@Override
	public final boolean behave() {
		JMudObject source = this.event.getSource();
		JMudObject target = this.event.getTarget();

		synchronized (System.out) {
			System.out.println("(" + this.getID() + ") GetBehavior.behave(): " + this.event.toString());
		}

		target.orphan();
		source.childrenAdd(target);

		// prep the 'response' JMudEvent
		JMudEvent jme = new JMudEvent(JMudEventType.GotEvent, target, source, JMudEventType.DisplayTextStdOutEvent);
		jme.getDataMap().put("displayText", source.getName() + " picks up the " + target.getName() + ".");

		synchronized (System.out) {
			System.out.println("(" + this.getID() + ") GetBehavior.behave() 'response': " + jme.toString());
		}
		jme.submitSelf();

		return true;
	}

	/**
	 * @see jmud.engine.behavior.Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
	@Override
	public final Behavior clone() {
		synchronized (System.out) {
			System.out.println("GetBehavior.clone()");
		}

		return new GetBehavior();
	}

}
