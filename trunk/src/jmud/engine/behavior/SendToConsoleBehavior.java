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
		this.eventTypesHandled.add(JMudEventType.SendToConsoleEvent);
	}

	/**
	 * @see jmud.engine.behavior.Behavior#behave()
	 * @return true
	 */
	@Override
	public boolean targetBehavior() {
		//Get the info off the event's dataMap
		String text = String.valueOf(this.event.getDataMap().get("textToConsole")).concat("");
		this.event.getTarget().sendToConsole(text);
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
	 * @return a new <code>DisplayTextStdOutBehavior</code>
	 */
	@Override
	public Behavior clone() {
		return new SendToConsoleBehavior(this.owner);
	}

}
