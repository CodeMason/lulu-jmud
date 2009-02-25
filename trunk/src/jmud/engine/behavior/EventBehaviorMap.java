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

	private Map<JMudEventType, List<BehaviorType>> eventBehaviorMap = Collections
			.synchronizedMap(new HashMap<JMudEventType, List<BehaviorType>>());

	/*
	 * Delegates
	 */
	
	public boolean containsEventType(JMudEventType jmet) {
		return eventBehaviorMap.containsKey(jmet);
	}

	public List<BehaviorType> getBehaviorTypes(JMudEventType jmet) {
		return eventBehaviorMap.get(jmet);
	}

	public Set<JMudEventType> getEventTypes() {
		return eventBehaviorMap.keySet();
	}

	public List<BehaviorType> removeBehaviorTypes(JMudEventType jmet) {
		return eventBehaviorMap.remove(jmet);
	}

	public void addMapping(JMudEventType jmet, BehaviorType bt) {
		//First get the List:
		List<BehaviorType> behaviorTypes = this.getBehaviorTypes(jmet);

		if (behaviorTypes == null) {
			behaviorTypes = Collections.synchronizedList(new ArrayList<BehaviorType>());
			behaviorTypes.add(bt);
			this.eventBehaviorMap.put(jmet,behaviorTypes);
		} else {
			behaviorTypes.add(bt);
		}
	}
	
	
	
	/*
	 * Helper FN
	 */
	public List<AbstractBehavior> getBehaviorsForEvent(JMudEventType jmet) {
		List<AbstractBehavior> out = Collections.synchronizedList(new ArrayList<AbstractBehavior>());
		
		List<BehaviorType> bts = this.getBehaviorTypes(jmet);
		
		if (bts == null) {
			return out;
		}
		
		for (BehaviorType bt : bts) {
			AbstractBehavior ab = BehaviorManager.getInstance().getBehavior(bt);
			if (ab != null){
				out.add(ab);
			}
		}
		
		return out;
	}
	
}
