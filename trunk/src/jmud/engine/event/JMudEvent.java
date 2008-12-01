package jmud.engine.event;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jmud.engine.behavior.Behavior;
import jmud.engine.job.definitions.AbstractJob;
import jmud.engine.object.JMudObject;

public class JMudEvent extends AbstractJob {

	private UUID eventID = null;
	private JMudEventType eventType = null;

	private transient JMudObject source;
	private transient JMudObject target;

	/**
	 * Generic map to handle any/all String named data that needs to accompany
	 * the Event.
	 */
	private Map<String, Object> dataMap = null;

	public JMudEvent(JMudEventType eventType, JMudObject source, JMudObject target) {
		super();
		this.eventID = UUID.randomUUID();
		this.eventType = eventType;
		this.source = source;
		this.target = target;

		this.dataMap = Collections.synchronizedMap(new HashMap<String, Object>());
	}

	/**
	 * The object on which the Event initially occurred.
	 * 
	 * @return The object on which the Event initially occurred.
	 */
	public JMudObject getSource() {
		return this.source;
	}

	/**
	 * The objects on which the Event is targeted.
	 * 
	 * @return The object on which the Event initially occurred.
	 */
	public JMudObject getTarget() {
		return this.target;
	}

	public UUID getEventID() {
		return this.eventID;
	}

	public JMudEventType getEventType() {
		return eventType;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	@Override
	public boolean doJob() {

		List<Behavior> behs = this.target.getBehaviors(this);

		synchronized (System.out) {
			System.out.println("JMudEvent.doJob():  EventType: " + this.getEventType() + 
					" Source: " + this.source.getName() + 
					" Target: " + this.target.getName());
		}
		

		if (behs != null) {
			for (Behavior b : behs) {
				Behavior newB = b.clone();
				newB.setEvent(this);
				newB.submitSelf();
			}
			return true;
		} else {
			return false;
		}
	}

}
