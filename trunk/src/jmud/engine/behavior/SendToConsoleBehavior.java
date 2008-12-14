package jmud.engine.behavior;

import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 *
 *
 * @author david.h.loman
 */
public class SendToConsoleBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public SendToConsoleBehavior(JMudObject owner) {
		super(owner);
		this.eventTypesHandled.add(JMudEventType.SendToConsole);
	}

	/**
	 * @see jmud.engine.behavior.Behavior#behave()
	 * @return true
	 */
	@Override
	public boolean targetBehavior() {
		String text = String.valueOf(this.event.getNamedEventParameters().get("textToConsole")).concat("");
		this.event.getTarget().sendToConsole(text);
		return true;
	}

	/**
	 * @see jmud.engine.behavior.Behavior#clone()
	 * @return a new <code>DisplayTextStdOutBehavior</code>
	 */
	@Override
	public Behavior clone() {
		return new SendToConsoleBehavior(this.owner);
	}

}
