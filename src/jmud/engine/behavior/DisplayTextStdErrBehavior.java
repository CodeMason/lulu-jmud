package jmud.engine.behavior;

import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 *
 *
 * @author david.h.loman
 */
public class DisplayTextStdErrBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public DisplayTextStdErrBehavior(JMudObject owner) {
		super(owner);
		this.eventTypesHandled.add(JMudEventType.DisplayTextStdErrEvent);
	}

	/**
	 * @see jmud.engine.behavior.Behavior#behave()
	 * @return true
	 */
	@Override
	public boolean targetBehavior() {
		JMudObject source = this.event.getSource();
		JMudObject target = this.event.getTarget();

		String text = String.valueOf(this.event.getDataMap().get("displayText"));

		System.err.println("DisplayTextStdOutBehavior(" + this.getID() + ")" + "\t Source: " + source.toStringShort()
				+ "\t Target: " + target.toStringShort() + "\n\t Text: " + text + "\n");
		return true;
	}

	@Override
	protected boolean ccBehavior() {
		return true;
	}

	@Override
	protected boolean sourceBehavior() {
		// loop back to ccBehavior()
		return this.ccBehavior();
	}

	/**
	 * @see jmud.engine.behavior.Behavior#clone()
	 * @return a new <code>DisplayTextStdErrBehavior</code>
	 */
	@Override
	public Behavior clone() {
		return new DisplayTextStdErrBehavior(this.owner);
	}

}
