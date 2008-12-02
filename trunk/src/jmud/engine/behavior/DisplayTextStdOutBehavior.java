package jmud.engine.behavior;

import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 * 
 * 
 * @author david.h.loman
 */
public class DisplayTextStdOutBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public DisplayTextStdOutBehavior() {
		super();
		this.eventTypesHandled.add(JMudEventType.DisplayTextStdOutEvent);
	}

	/**
	 * @see jmud.engine.behavior.Behavior#clone()
	 * @return a new <code>DisplayTextStdOutBehavior</code>
	 */
	@Override
	public Behavior clone() {
		return new DisplayTextStdOutBehavior();
	}

	/**
	 * @see jmud.engine.behavior.Behavior#behave()
	 * @return true
	 */
	@Override
	public boolean behave() {
		JMudObject source = this.event.getSource();
		JMudObject target = this.event.getTarget();

		String text = this.event.getDataMap().get("displayText") + "";

		System.out.println("DisplayTextStdOutBehavior(" + this.getID() + ")" + "\t Source: " + source.toStringShort()
				+ "\t Target: " + target.toStringShort() + "\n\t Text: " + text + "\n");
		return true;
	}

}
