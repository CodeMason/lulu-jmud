package jmud.engine.event;

import java.util.UUID;
import jmud.engine.object.JMudObject;

/**
 * This serves as a Struct for linking both Target and Source JMudObjects with a JMudEvent
 * It is *NOT* synchronized in anyway.
 * 
 * @author David Loman
 *
 */

public class JMudEventSubscription {

	//UUID
	UUID SubscriptionID = UUID.randomUUID();
	
	//If this JMudObject...
	private JMudObject source = null;
	
	//generates this JMudEvent...
	private JMudEventType eventType = null;
	
	//Then also send the event to this JMudObject...
	private JMudObject target = null;

	public JMudEventSubscription(JMudEventType eventType, JMudObject source, JMudObject target) {
		super();
		this.eventType = eventType;
		this.source = source;
		this.target = target;
	}

	public JMudObject getSource() {
		return source;
	}

	public JMudEventType getEventType() {
		return eventType;
	}

	public JMudObject getTarget() {
		return target;
	}

	public UUID getSubscriptionID() {
		return SubscriptionID;
	}
	
	
}
