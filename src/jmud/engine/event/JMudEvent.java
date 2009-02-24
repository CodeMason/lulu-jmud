package jmud.engine.event;

import jmud.engine.behavior.definitions.AbstractBehavior;
import jmud.engine.job.definitions.RunBehaviorJob;
import jmud.engine.object.JMudObject;
import jmud.engine.object.JMudObjectUtils;

import java.util.*;

public class JMudEvent {

	private UUID eventID;

	private JMudEventType eventType;

	private final transient JMudObject source;
	private final transient JMudObject target;
	private AffectRange affRange;

	Set<JMudObject> targetAffected;
	Set<JMudObject> sourceAffected;
	Set<JMudObject> allAffected;

	public JMudEvent(final JMudEventType eventType, final JMudObject source, final JMudObject target) {
		this(eventType, source, target, new AffectRange());
	}

	public JMudEvent(final JMudEventType eventType, final JMudObject source, final JMudObject target, AffectRange ar) {
		this.eventType = eventType;
		this.source = source;
		this.target = target;
		this.affRange = ar;
		this.eventID = UUID.randomUUID();

		// Call this upon JMudEvent instantiation in order to 'lock in' all the
		// JMudObjects affected since instantiation and runEvent() may happen at
		// different times
		this.makeAffectedSets();
	}

	public boolean runEvent() {

		synchronized (System.out) {
			System.out.println("Running a JMudEvent::" + this.eventType);
		}

		for (JMudObject j : this.allAffected) {

			// Get all of the mappings for this eventType that this object has.
			List<AbstractBehavior> abs = j.getEventBehaviorMap().getBehaviorsForEvent(this.eventType);

			for (AbstractBehavior ab : abs) {
				RunBehaviorJob rbj = new RunBehaviorJob(j, ab, this);
				rbj.selfSubmit();

			}
		}
		return true;
	}

	private void makeAffectedSets() {
		// Get all the affected JMudObjects in the area of the
		// Target and Source based on the AffectRange
		this.targetAffected = JMudObjectUtils.getJmosAffected(this.target, this.affRange);
		this.sourceAffected = JMudObjectUtils.getJmosAffected(this.source, this.affRange);

		// Compile the two sets into one for ease of use.
		this.allAffected = new HashSet<JMudObject>(this.targetAffected);
		this.allAffected.addAll(this.sourceAffected);

	}

	/*
	 * Getters n Setters
	 */

	public UUID getEventID() {
		return eventID;
	}

	public JMudEventType getEventType() {
		return eventType;
	}

	public JMudObject getSource() {
		return source;
	}

	public JMudObject getTarget() {
		return target;
	}

	public AffectRange getPerRange() {
		return affRange;
	}

	public Set<JMudObject> getTargetAffected() {
		return targetAffected;
	}

	public Set<JMudObject> getSourceAffected() {
		return sourceAffected;
	}

	public Set<JMudObject> getAllAffected() {
		return allAffected;
	}

	// @Override
	// public String toString() {
	// return new
	// StringBuilder().append("EventID: ").append(this.getUUID()).append
	// ("\t EventType: ").append(
	// this.targetEventType).append("\t Source: (").append(this.source.
	// toStringShort()).append(")").append(
	//"\t Target: (").append(this.target.toStringShort()).append(")").toString()
	// ;
	// }
}
