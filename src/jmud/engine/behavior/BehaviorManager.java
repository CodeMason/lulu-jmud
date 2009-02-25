package jmud.engine.behavior;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jmud.engine.behavior.definitions.AbstractBehavior;

public class BehaviorManager {
	/*
	 * BEGIN Singleton Implementation
	 */
	/**
	 * <code>Holder</code> is loaded on the first execution of
	 * <code>BehaviorRegistrar.getInstance()</code> or the first access to
	 * <code>Holder.INSTANCE</code>, not before.
	 */
	private static final class Holder {
		/**
		 * The singleton instance.
		 */
		private static final BehaviorManager INSTANCE = new BehaviorManager();

		/**
		 * <code>Holder</code> is a utility class. Disallowing public/default
		 * constructor.
		 */
		private Holder() {

		}
	}

	/**
	 * @return the singleton instance
	 */
	public static BehaviorManager getInstance() {
		return Holder.INSTANCE;
	}

	private BehaviorManager() {
	}

	/*
	 * END Singleton Implementation
	 */

	/*
	 * BehaviorType to AbstractBehavior mapping.
	 */
	private Map<BehaviorType, AbstractBehavior> typeBehaviorMap = Collections
			.synchronizedMap(new HashMap<BehaviorType, AbstractBehavior>());

	
	/*
	 * typeBehaviorMap Delegates
	 */
	public boolean containsBehaviorType(BehaviorType bt) {
		return typeBehaviorMap.containsKey(bt);
	}

	public AbstractBehavior getBehavior(BehaviorType bt) {
		return typeBehaviorMap.get(bt);
	}

	public Set<BehaviorType> getAllRegisteredBehaviorTypes() {
		return typeBehaviorMap.keySet();
	}

	public AbstractBehavior register(BehaviorType bt, AbstractBehavior ab) {
		return typeBehaviorMap.put(bt, ab);
	}

	public AbstractBehavior removeBehaviorType(BehaviorType bt) {
		return typeBehaviorMap.remove(bt);
	}


	

}
