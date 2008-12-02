package jmud.engine.event;

import java.util.UUID;

import jmud.engine.object.JMudObject;

/**
 * This serves as a data class for linking both target and source
 * <code>JMudObjects</code> with a <code>JMudEvent</code>. It is *NOT*
 * synchronized in anyway.
 * 
 * @author David Loman
 */

public class JMudEventSubscription {

	/**
	 * If this JMudObject...
	 */
	private JMudObject source = null;

	/**
	 * Generates this JMudEvent...
	 */
	private JMudEventType eventType = null;

	/**
	 * Then also send the event to this JMudObject...
	 */
	private JMudObject ccTarget = null;

	/**
	 * UUID.
	 */
	UUID SubscriptionID = UUID.randomUUID();

	public JMudEventSubscription(final JMudEventType eventType, final JMudObject source, final JMudObject target) {
		super();
		this.eventType = eventType;
		this.source = source;
		this.ccTarget = target;
	}

	public final JMudEventType getEventType() {
		return eventType;
	}

	public final JMudObject getSource() {
		return source;
	}

	public final UUID getSubscriptionID() {
		return SubscriptionID;
	}

	public final JMudObject getCCTarget() {
		return ccTarget;
	}

}
