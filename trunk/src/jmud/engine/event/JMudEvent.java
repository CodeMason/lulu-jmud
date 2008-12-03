package jmud.engine.event;

import jmud.engine.behavior.Behavior;
import jmud.engine.job.definitions.AbstractJob;
import jmud.engine.object.JMudObject;

import java.util.*;

public class JMudEvent extends AbstractJob {
	private JMudEventType targetEventType;

	
	private final transient JMudObject source;
	private final transient JMudObject target;

	/**
	 * Generic map to handle any/all String named data that needs to accompany
	 * the Event.
	 */
	//Always initialize non-final's, even if its to null;
	private Map<String, Object> dataMap = null;
	
	public JMudEvent(final JMudEventType eventType, final JMudObject source, final JMudObject target) {
        this.targetEventType = eventType;
		this.source = source;
		this.target = target;

		this.dataMap = Collections.synchronizedMap(new HashMap<String, Object>());
	}

	@Override
	public boolean doJob() {

		// Build objects to send Event to List:
		Set<JMudObject> ccObjs = new HashSet<JMudObject>();

		// Get all the siblings 
		// QQQ CM: including self?
		// AAA DL:Yuppers!  that's where 'pcSteve picks YOU up' comes from!
		ccObjs.addAll(this.source.getParent().childrenValues());
		ccObjs.addAll(this.target.getParent().childrenValues());

		// Get anything registered
		ccObjs.addAll(JMudEventRegistrar.getInstance().getTargetJMudObjectBySourceAndEvent(this.target, this.getEventType()));

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

			if (!behs.isEmpty()) {
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
				//Set flag to false to show that this event did NOT evoke behavior from every object.
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
		return new StringBuilder()
           .append("EventID: ")
           .append(this.getID())
           .append("\t EventType: ")
           .append(this.targetEventType)
           .append("\t Source: (")
           .append(this.source.toStringShort())
           .append(")")
           .append("\t Target: (")
           .append(this.target.toStringShort())
           .append(")")
           .toString();
	}
}
