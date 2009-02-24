package jmud.engine.behavior;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jmud.engine.behavior.definitions.AbstractBehavior;

public class BehaviorRegistrar {
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
		private static final BehaviorRegistrar INSTANCE = new BehaviorRegistrar();

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
	public static BehaviorRegistrar getInstance() {
		return Holder.INSTANCE;
	}

	private BehaviorRegistrar() {
	}

	/*
	 * END Singleton Implementation
	 */

	/*
	 * BehaviorType to AbstractBehavior mapping.
	 */
	private Map<BehaviorType, AbstractBehavior> behaviorGens = Collections
			.synchronizedMap(new HashMap<BehaviorType, AbstractBehavior>());

	public boolean containsBehaviorType(BehaviorType key) {
		return behaviorGens.containsKey(key);
	}

	public AbstractBehavior getBehavior(BehaviorType key) {
		return behaviorGens.get(key);
	}

	public Set<BehaviorType> getAllRegisteredBehaviorTypes() {
		return behaviorGens.keySet();
	}

	public AbstractBehavior register(BehaviorType key, AbstractBehavior value) {
		return behaviorGens.put(key, value);
	}

	public AbstractBehavior removeBehaviorType(BehaviorType key) {
		return behaviorGens.remove(key);
	}


	

}
