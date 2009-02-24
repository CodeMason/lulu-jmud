package jmud.engine.event;

import jmud.engine.behavior.AbstractBehavior;
import jmud.engine.behavior.BehaviorRegistrar;
import jmud.engine.job.definitions.AbstractJob;
import jmud.engine.job.definitions.RunBehaviorJob;
import jmud.engine.object.JMudObject;

import java.util.*;

public class JMudEvent {
	private JMudEventType targetEventType;

	private final transient JMudObject source;
	private final transient JMudObject target;
	


	public JMudEvent(final JMudEventType eventType, final JMudObject source, final JMudObject target) {
		this.targetEventType = eventType;
		this.source = source;
		this.target = target;
	}

	public boolean runEvent() {
        
		synchronized (System.out) {
			System.out.println("Running a JMudEvent::" + this.targetEventType);
		}

        Set<JMudObject> objectsToNotify = new HashSet<JMudObject>();
        
        //add source's siblings
		objectsToNotify.addAll(this.source.getParentObject().getAllChildren());
		
		//add target's siblings
		objectsToNotify.addAll(this.target.getParentObject().getAllChildren());
		
		// Set success flag
		boolean hasCompletedSuccessfully = true;

		for (JMudObject ccJmo : objectsToNotify) {

			AbstractBehavior ccJmoBehavior = ccJmo.getBehavior(this.getEventType());

			if (ccJmoBehavior != null) {
				
				RunBehaviorJob rbj = new RunBehaviorJob(ccJmoBehavior);
				rbj.selfSubmit();
				
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
