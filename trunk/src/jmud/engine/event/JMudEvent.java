package jmud.engine.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jmud.engine.behavior.Behavior;
import jmud.engine.job.definitions.AbstractJob;
import jmud.engine.object.JMudObject;

public class JMudEvent extends AbstractJob {
	private JMudEventType targetEventType = null;

	private transient final JMudObject source;
	private transient final JMudObject target;

	/**
	 * Generic map to handle any/all String named data that needs to accompany
	 * the Event.
	 */
	private Map<String, Object> dataMap = null;

	public JMudEvent(final JMudEventType eventType, final JMudObject source, final JMudObject target) {
		super();
		this.targetEventType = eventType;
		this.source = source;
		this.target = target;

		this.dataMap = Collections.synchronizedMap(new HashMap<String, Object>());
	}

	@Override
	public final boolean doJob() {

		// Build objects to send Event to List:
		Set<JMudObject> ccObjs = new HashSet<JMudObject>();

		// Get all the siblings
		ccObjs.addAll(this.source.getParent().childrenValues());
		ccObjs.addAll(this.target.getParent().childrenValues());

		// Get anything registered
		ccObjs.addAll(JMudEventRegistrar.getInstance().getTargetJMudObjectBySourceAndEvent(this.target,
				this.getEventType()));

		// Set success flag
		boolean allFinishedTrue = true;

		// Iterate over the list:
		for (JMudObject jmo : ccObjs) {

			// Now Handle the Target
			List<Behavior> behs = jmo.getBehaviors(this.getEventType());

			if (behs == null) {
				behs = new ArrayList<Behavior>();
			}

			synchronized (System.out) {
				System.out.println("(" + this.getID() + ") JMudEvent.doJob(): Found " + behs.size() + " behaviors of type "
						+ this.getEventType() + " from " + jmo.toStringShort() + " to run.");
			}

			if (behs.size() != 0) {
				for (Behavior b : behs) {
					Behavior newB = b.clone();
					newB.setEvent(this);

					synchronized (System.out) {
						System.out.println("(" + this.getID() + ") Behavior Cloning: " + b.toString()
								+ " was cloned into: " + newB.toString());
					}
					newB.submitSelf();
				}

			} else {
				//Set flag to false to show that this event didNOT evoke behavior from every object.
				allFinishedTrue = false;
			}
		}
		return allFinishedTrue;
	}

	public final Map<String, Object> getDataMap() {
		return dataMap;
	}

	public final JMudEventType getEventType() {
		return targetEventType;
	}

	/**
	 * The object on which the Event initially occurred.
	 * 
	 * @return The object on which the Event initially occurred.
	 */
	public final JMudObject getSource() {
		return this.source;
	}

	/**
	 * The objects on which the Event is targeted.
	 * 
	 * @return The object on which the Event initially occurred.
	 */
	public final JMudObject getTarget() {
		return this.target;
	}

	@Override
	public String toString() {
		String out = "";
		out += "EventID: " + this.getID();
		out += "\t EventType: " + this.targetEventType;
		out += "\t Source: (" + this.source.toStringShort() + ")";
		out += "\t Target: (" + this.target.toStringShort() + ")";

		return out;
	}
}
