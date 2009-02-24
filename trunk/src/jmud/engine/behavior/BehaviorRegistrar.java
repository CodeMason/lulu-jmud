package jmud.engine.behavior;

import java.util.Collection;
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
	
	private Map<String, BehaviorGenerator<? extends AbstractBehavior>> behaviorGens = Collections
			.synchronizedMap(new HashMap<String, BehaviorGenerator<? extends AbstractBehavior>>());

	public boolean containsBehaviorGen(String name) {
		return this.behaviorGens.containsKey(name);
	}

	public boolean containsBehaviorGen(
			BehaviorGenerator<? extends AbstractBehavior> bg) {
		return this.behaviorGens.containsValue(bg);
	}

	public Set<String> getListOfBehaviorGenNames() {
		return this.behaviorGens.keySet();
	}

	public BehaviorGenerator<? extends AbstractBehavior> registerBehaviorGen(
			BehaviorGenerator<? extends AbstractBehavior> bg) {
		return this.behaviorGens.put(bg.getClassName(), bg);
	}

	public BehaviorGenerator<? extends AbstractBehavior> remBehaviorGen(String behaviorName) {
		return this.behaviorGens.remove(behaviorName);
	}

	public Collection<BehaviorGenerator<? extends AbstractBehavior>> getAllBehaviorGens() {
		return behaviorGens.values();
	}

}
