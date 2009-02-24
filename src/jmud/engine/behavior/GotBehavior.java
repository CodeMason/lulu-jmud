package jmud.engine.behavior;

import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 *
 *
 * @author david.h.loman
 */
public class GotBehavior extends AbstractBehavior {

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
	 * @see jmud.engine.behavior.AbstractBehavior#behave()
	 * @return true
	 */
	@Override
	public final boolean targetBehavior() {
		// What the owner sees

		String txt = "You get the " + this.event.getSource().getDisplayedName();
		this.event.getTarget().sendTextToObject(txt);

		return true;
	}

    /**
     * Anything a non-participant does
     * @return
     */
    @Override
	protected boolean bystanderBehavior() {
		// What anyone else sees.
	   	String txt = this.event.getTarget().getDisplayedName() + " gets the " + this.event.getSource().getDisplayedName();
		this.owner.sendTextToObject(txt);

		return true;
	}

    /**
     * Anything the "gotten" object does
     * @return
     */
    @Override
	protected boolean sourceBehavior() {
		// What You see.

    	String txt = this.event.getTarget().getDisplayedName() + " picks YOU up!!";
		this.event.getSource().sendTextToObject(txt);


		return true;
	}
}
