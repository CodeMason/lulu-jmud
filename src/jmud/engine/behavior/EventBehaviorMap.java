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

	public List<BehaviorType> removeBehaviorTypes(JMudEventType jmet) {
		return behaviorMap.remove(jmet);
	}

	public void addMapping(JMudEventType jmet, BehaviorType bt) {
		//First get the List:
		List<BehaviorType> bs = this.getBehaviorTypeList(jmet);

		if (bs == null) {
			bs = Collections.synchronizedList(new ArrayList<BehaviorType>());
			bs.add(bt);
			this.behaviorMap.put(jmet,bs);
		} else {
			bs.add(bt);
		}
	}
	
	
	
	/*
	 * Helper FN
	 */
	public List<AbstractBehavior> getBehaviorsForEvent(JMudEventType jmet) {
		List<AbstractBehavior> out = Collections.synchronizedList(new ArrayList<AbstractBehavior>());
		
		List<BehaviorType> bts = this.getBehaviorTypeList(jmet);
		
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
