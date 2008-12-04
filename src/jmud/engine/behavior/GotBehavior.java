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
     * What the "getter" does (i.e. the JMudObject getting something)
     *
	 * @see jmud.engine.behavior.Behavior#behave()
	 * @return true
	 */
	@Override
	public final boolean targetBehavior() {
		// What the owner sees
		
		String txt = "You get the " + this.event.getSource().getName();
		this.event.getTarget().sendToConsole(txt);

		return true;
	}

    /**
     * Anything a non-participant does
     * @return
     */
    @Override
	protected boolean ccBehavior() {
		// What anyone else sees.
	   	String txt = this.event.getTarget().getName() + " gets the " + this.event.getSource().getName();
		this.owner.sendToConsole(txt);

		return true;
	}

    /**
     * Anything the "gotten" object does
     * @return
     */
    @Override
	protected boolean sourceBehavior() {
		// What You see.

    	String txt = this.event.getTarget().getName() + " picks YOU up!!";
		this.event.getSource().sendToConsole(txt);


		return true;
	}

	/**
	 * @see jmud.engine.behavior.Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
	@Override
	public final Behavior clone() {
		return new GotBehavior(this.owner);
	}
}
