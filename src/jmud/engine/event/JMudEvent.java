package jmud.engine.event;

import jmud.engine.behavior.Behavior;
import jmud.engine.job.definitions.AbstractJob;
import jmud.engine.object.JMudObject;

import java.util.*;

public class JMudEvent extends AbstractJob {
	private JMudEventType targetEventType;

	private final transient JMudObject source;
	private final transient JMudObject target;

	private Map<String, Object> namedEventParameters = null;

	public JMudEvent(final JMudEventType eventType, final JMudObject source, final JMudObject target) {
		this.targetEventType = eventType;
		this.source = source;
		this.target = target;

		this.namedEventParameters = Collections.synchronizedMap(new HashMap<String, Object>());
	}

	@Override
	public boolean doJob() {
        Behavior newB;
		synchronized (System.out) {
			System.out.println("Running a JMudEvent::" + this.targetEventType);
		}

        Set<JMudObject> objectsToNotify = new HashSet<JMudObject>();
		objectsToNotify.addAll(this.source.getParentObject().getChildObjects().values());
		objectsToNotify.addAll(this.target.getParentObject().getChildObjects().values());
		objectsToNotify.addAll(JMudEventRegistrar.getLazyLoadedInstance().getTargetObjects(this.target, this.getEventType()));

		// Set success flag
		boolean hasCompletedSuccessfully = true;

		for (JMudObject ccObject : objectsToNotify) {

			List<Behavior> ccObjectBehaviors = ccObject.getBehaviors(this.getEventType());

			if (ccObjectBehaviors == null) {
				ccObjectBehaviors = new ArrayList<Behavior>();
			}

			if (!ccObjectBehaviors.isEmpty()) {
				for (Behavior b : ccObjectBehaviors) {
                    newB = b.clone();
                    newB.setEvent(this);
					newB.selfSubmit();
				}

			} else {
				hasCompletedSuccessfully = false;
			}
		}
		return hasCompletedSuccessfully;
	}

	public final Map<String, Object> getNamedEventParameters() {
		return namedEventParameters;
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
		return new StringBuilder().append("EventID: ").append(this.getUUID()).append("\t EventType: ").append(
				this.targetEventType).append("\t Source: (").append(this.source.toStringShort()).append(")").append(
				"\t Target: (").append(this.target.toStringShort()).append(")").toString();
	}
}
