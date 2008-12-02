package jmud.engine.event;

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
	private JMudEventType ccEventType = null;

	private transient final JMudObject source;
	private transient final JMudObject target;

	/**
	 * Generic map to handle any/all String named data that needs to accompany
	 * the Event.
	 */
	private Map<String, Object> dataMap = null;

	public JMudEvent(final JMudEventType eventType, final JMudObject source, final JMudObject target,
			JMudEventType ccEventtype) {
		super();
		this.targetEventType = eventType;
		this.source = source;
		this.target = target;
		this.ccEventType = ccEventtype;

		this.dataMap = Collections.synchronizedMap(new HashMap<String, Object>());
	}

	public JMudEvent(final JMudEventType eventType, final JMudObject source, final JMudObject target) {
		this(eventType, source, target, null);
	}

	@Override
	public final boolean doJob() {

		boolean retVal = this.handleTarget();
		this.handleCC();
		return retVal;
	}

	private void handleCC() {

		if (this.ccEventType == JMudEventType.DisplayTextStdOutEvent) {
			System.out.println();
		}

		// CC all the target and source siblings

		// Do a null check to see if we WANT to inform anyone else.
		if (this.ccEventType != null) {
			System.out.println("(" + this.getID() + ") JMudEvent.doJob(): There was a ccEventType for Event.");

			// Build list of JMudObjects to send event to:
			Set<JMudObject> ccObjs = new HashSet<JMudObject>();

			// Get all the siblings
			ccObjs.addAll(this.source.getParent().childrenValues());
			ccObjs.addAll(this.target.getParent().childrenValues());

			// No need to inform the source or target
			ccObjs.remove(this.source);
			ccObjs.remove(this.target);

			for (JMudObject tgt : ccObjs) {
				// ENSURE there is no CC value on a CC Event! Otherwise an
				// exponential runaway might occur!
				JMudEvent jme = new JMudEvent(this.ccEventType, this.source, tgt);
				jme.getDataMap().putAll(this.dataMap);

				System.out.println("(" + this.getID() + ") New CC event: " + jme.toString());

				jme.submitSelf();
			}

		} else {
			System.out.println("(" + this.getID() + ") JMudEvent.doJob(): There was no ccEventType for Event.");
		}
	}

	private boolean handleTarget() {
		// Now Handle the Target
		List<Behavior> behs = this.target.getBehaviors(this.getEventType());

		synchronized (System.out) {
			System.out.println("(" + this.getID() + ") JMudEvent.doJob(): " + this.toString());
			if (behs != null) {
				System.out.println("(" + this.getID() + ") JMudEvent.doJob(): Found " + behs.size()
						+ " behaviors to run.");
			} else {
				System.out.println("(" + this.getID() + ") JMudEvent.doJob(): Found 0 behaviors(" + this.getEventType()
						+ ") from " + this.target.toStringShort() + " to run.");
			}
		}

		if (behs != null) {
			for (Behavior b : behs) {
				Behavior newB = b.clone();
				newB.setEvent(this);

				synchronized (System.out) {
					System.out.println("(" + this.getID() + ") Behavior Cloning: " + b.toString()
							+ " was cloned into: " + newB.toString());
				}

				newB.submitSelf();
			}
			return true;
		} else {
			return false;
		}

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
