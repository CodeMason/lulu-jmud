package jmud.engine.stats;

/**
 * Repository for all AbstractStatDef objects (and any subclass objects).
 * Allow for ease of addition, look up and removal.
 *
 * @author David Loman
 * @version 0.1
 */
import java.util.HashMap;

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

	private final HashMap<String, AbstractStatDef> statDefMap = new HashMap<String, AbstractStatDef>();

	public void init() {
	}

	public void addStatDef(AbstractStatDef asd) {
		synchronized (this.statDefMap) {
			this.statDefMap.put(asd.getName(), asd);
		}
	}

	public AbstractStatDef getStatDef(String name) {
		AbstractStatDef asdc = null;
		synchronized (this.statDefMap) {
			asdc = this.statDefMap.get(name);
		}
		return asdc;
	}

	public AbstractStatDef remStatDef(String name) {
		AbstractStatDef asd = null;
		synchronized (this.statDefMap) {
			asd = this.statDefMap.remove(name);
		}
		return asd;
	}

}
