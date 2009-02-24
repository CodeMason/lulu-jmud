package jmud.engine.behavior;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jmud.engine.behavior.definitions.AbstractBehavior;
import jmud.engine.event.JMudEventType;

public class BehaviorMap {
	/**
	 * A mapping of events to behaviors
	 */
	private Map<JMudEventType, AbstractBehavior> behaviorMap = Collections
			.synchronizedMap(new HashMap<JMudEventType, AbstractBehavior>());

	/*
	 * Behavior Map Routines
	 */

	public boolean containsBehavior(AbstractBehavior b) {
		return behaviorMap.containsValue(b);
	}

	public AbstractBehavior getBehavior(JMudEventType jmet) {
		return behaviorMap.get(jmet);
	}

	public Set<JMudEventType> getEventTypes() {
		return behaviorMap.keySet();
	}

	public AbstractBehavior addBehavior(JMudEventType jmet, AbstractBehavior b) {
		return behaviorMap.put(jmet, b);
	}

	public AbstractBehavior removeBehavior(JMudEventType jmet) {
		return behaviorMap.remove(jmet);
	}

	public Set<AbstractBehavior> getBehaviors() {
		return (Set<AbstractBehavior>) behaviorMap.values();
	}

}
