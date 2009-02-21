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
		// EventType.Get
		this.eventTypesHandled.add(JMudEventType.Got);
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

		String txt = "You get the " + this.event.getSource().getHumanReadableName();
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
	   	String txt = this.event.getTarget().getHumanReadableName() + " gets the " + this.event.getSource().getHumanReadableName();
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

    	String txt = this.event.getTarget().getHumanReadableName() + " picks YOU up!!";
		this.event.getSource().sendToConsole(txt);


		return true;
	}
}
