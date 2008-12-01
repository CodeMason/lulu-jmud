package jmud.engine.event.deprecated;

import jmud.engine.event.JMudEventType;
import jmud.engine.event.JMudEvent;
import jmud.engine.object.JMudObject;

/**
 * An event to return upon the successful completion of a behavior from the
 * perspective of a player: e.g. When successfully sitting on an object a
 * SuccessEvent might have a message that says "You sit down on the [object]"
 * 
 * Not to be confused with successfully processing the Behavior's code.
 * 
 * A fantastic example of this is the "spit" command from Space Quest 2: you can
 * spit, but it probably won't succeed like you think it will.
 */
public class SuccessEvent extends JMudEvent {
	private String message;

	/**
	 * Constructs a Success Event.
	 * 
	 * @param source
	 *            The object on which the Event initially occurred.
	 * @throws IllegalArgumentException
	 *             if source is null.
	 */
	public SuccessEvent(JMudObject source) {
		this(source, null);
	}

	public SuccessEvent(JMudObject source, String message) {
		super(JMudEventType.SuccessEvent, source, null);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
