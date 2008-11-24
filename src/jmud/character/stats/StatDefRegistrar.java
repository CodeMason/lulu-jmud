package jmud.character.stats;

import java.util.HashMap;

public class StatDefRegistrar {
	/*
	 * ********************************************
	 * Singleton Implementation
	 * ********************************************
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
	 * ********************************************
	 * Static Class Implementation
	 * ********************************************
	 */

	private final HashMap<String, StatDef> statDefMap = new HashMap<String, StatDef>();


	public void init () {

	}

	public void addStatDef(String name, StatDef cmd) {
		synchronized (this.statDefMap) {
			this.statDefMap.put(name, cmd);
		}
	}

	public StatDef getStatDef(String name) {
		StatDef c = null;
		synchronized (this.statDefMap) {
			c = this.statDefMap.get(name);
		}
		return c;
	}

	public StatDef remStatDef(String name) {
		StatDef c = null;
		synchronized (this.statDefMap) {
			c = this.statDefMap.remove(name);
		}
		return c;
	}

}

















