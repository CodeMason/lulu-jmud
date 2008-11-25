package jmud.engine.stats;

/**
 * Repository for all AbstractStatDef objects (and any subclass objects).
 * Allow for ease of addition, look up and removal.
 *
 * @author David Loman
 * @version 0.1
 */
import java.util.HashMap;
import java.util.HashSet;

import jmud.engine.core.Namespace;

public class StatDefRegistrar {
	/*
	 * 
	 * Singleton Implementation
	 */

	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected StatDefRegistrar() {
	}

	/**
	 * StatDefRegistrarHolder is loaded on the first execution of
	 * StatDefRegistrar.getInstance() or the first access to
	 * StatDefRegistrarHolder.INSTANCE, not before.
	 */
	private static class StatDefRegistrarHolder {
		private final static StatDefRegistrar INSTANCE = new StatDefRegistrar();
	}

	public static StatDefRegistrar getInstance() {
		return StatDefRegistrarHolder.INSTANCE;
	}

	/*
	 * 
	 * Static Class Implementation
	 */

	// StatDef name to Object reference mapping
	private final HashMap<String, AbstractStatDef> statDefNameToStatDefMap = new HashMap<String, AbstractStatDef>();

	// Namespace to Object reference Set mapping
	private final HashMap<Namespace, HashSet<AbstractStatDef>> namespaceToStatDefSetMap = new HashMap<Namespace, HashSet<AbstractStatDef>>();

	public void init() {
	}

	public void addStatDef(AbstractStatDef asd) {
		// Add to the DefMap
		this.addStatDefToDefMap(asd);

		for (Namespace ns : asd.getNamespaces()) {
			this.addStatDefToSetMap(asd, ns);
		}

	}

	public AbstractStatDef getStatDef(String name) {
		AbstractStatDef asdc = null;
		synchronized (this.statDefNameToStatDefMap) {
			asdc = this.statDefNameToStatDefMap.get(name);
		}
		return asdc;
	}

	public AbstractStatDef remStatDef(String name) {
		AbstractStatDef asd = this.remStatDefFromDefMap(name);

		for (Namespace ns : asd.getNamespaces()) {
			this.remStatDefFromSetMap(asd, ns);
		}

		return asd;
	}

	public HashSet<AbstractStatDef> getAllDefsInNamespace(Namespace ns) {
		HashSet<AbstractStatDef> out = null;
		synchronized (this.namespaceToStatDefSetMap) {
			out = this.namespaceToStatDefSetMap.get(ns);
		}
		return out;
	}

	public AbstractStatDef remStatDefFromDefMap(String name) {
		AbstractStatDef asd = null;
		synchronized (this.statDefNameToStatDefMap) {
			asd = this.statDefNameToStatDefMap.remove(name);
		}
		return asd;

	}

	public void remStatDefFromSetMap(AbstractStatDef asd, Namespace ns) {
		// get a handle on the HashSet
		HashSet<AbstractStatDef> hs = this.getAllDefsInNamespace(ns);

		// If the namespace isn't on the map yet, then no worries.
		if (hs != null) {
			// But if there is, pull out our asd from the set.
			synchronized (hs) {
				hs.remove(asd);
			}
		}

		return;
	}

	private void addStatDefToDefMap(AbstractStatDef asd) {
		synchronized (this.statDefNameToStatDefMap) {
			this.statDefNameToStatDefMap.put(asd.getName(), asd);
		}
	}

	private void addStatDefToSetMap(AbstractStatDef asd, Namespace ns) {
		// Add to the SetMap
		HashSet<AbstractStatDef> hs = this.getAllDefsInNamespace(ns);

		// If the namespace isn't on the map yet, then make a new HasSet
		if (hs == null) {
			hs = new HashSet<AbstractStatDef>();

			// Add the reference to the AbstractStatDef to the Set
			synchronized (hs) {
				hs.add(asd);
			}

			// Now add the Set to the Map:
			synchronized (this.namespaceToStatDefSetMap) {
				this.namespaceToStatDefSetMap.put(ns, hs);
			}
		} else {
			// There already is a HashSet
			synchronized (hs) {
				hs.add(asd);
			}
		}
	}

}
