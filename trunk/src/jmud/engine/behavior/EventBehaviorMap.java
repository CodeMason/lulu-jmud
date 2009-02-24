package jmud.engine.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jmud.engine.behavior.definitions.AbstractBehavior;
import jmud.engine.event.JMudEventType;

public class EventBehaviorMap {
	/**
	 * A mapping of events to behaviors
	 */
	private Map<JMudEventType, List<BehaviorType>> behaviorMap = Collections
			.synchronizedMap(new HashMap<JMudEventType, List<BehaviorType>>());

	public boolean containsEventType(JMudEventType jmet) {
		return behaviorMap.containsKey(jmet);
	}

	public List<BehaviorType> getBehaviorTypeList(JMudEventType jmet) {
		return behaviorMap.get(jmet);
	}

	public Set<JMudEventType> getEventTypes() {
		return behaviorMap.keySet();
	}

	public List<BehaviorType> putEventBehaviorMapping(JMudEventType jmet, List<BehaviorType> bt) {
		return behaviorMap.put(jmet, bt);
	}

	public List<BehaviorType> removeBehaviorTypes(JMudEventType jmet) {
		return behaviorMap.remove(jmet);
	}

	/*
	 * Helper FN
	 */
	public List<AbstractBehavior> getBehaviorsForEvent(JMudEventType jmet) {
		List<AbstractBehavior> out = Collections.synchronizedList(new ArrayList<AbstractBehavior>());
		
		List<BehaviorType> bts = this.getBehaviorTypeList(jmet);
		
		for (BehaviorType bt : bts) {
			AbstractBehavior ab = BehaviorRegistrar.getInstance().getBehavior(bt);
			if (ab != null){
				out.add(ab);
			}
		}
		
		return out;
	}
	
}
