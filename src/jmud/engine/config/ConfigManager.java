package jmud.engine.config;

import java.util.HashMap;
import java.util.Set;

/**
 * ConfigManager is a Singleton patterned class designed to store, facilitate
 * easy lookup, and execution of Configs.
 * 
 */

public class ConfigManager {
	/*
	 * 
	 * Singleton Implementation
	 */

	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected ConfigManager() {
	}

	/**
	 * ConfigManagerHolder is loaded on the first execution of
	 * ConfigManager.getInstance() or the first access to
	 * ConfigManagerHolder.INSTANCE, not before.
	 */
	private static class ConfigManagerHolder {
		private static final ConfigManager INSTANCE = new ConfigManager();
	}

	public static ConfigManager getInstance() {
		return ConfigManagerHolder.INSTANCE;
	}

	/*
	 * 
	 * Concrete Class Implementation
	 */

	private HashMap<String, AbstractConfig> configMap = new HashMap<String, AbstractConfig>();

	public void addConfig(AbstractConfig c) {
		this.configMap.put(c.getName(), c);

	}

	public void remConfig(AbstractConfig c) {
		this.configMap.remove(c.getName());
	}

	public AbstractConfig getConfigByName(String name) {
		return this.configMap.get(name);
	}

	public boolean executeConfigs() {
		//retVal is a complete process flag.  Will =TRUE if ALL configs load properly.
		//will =FALSE if ANY fail.
		boolean retVal = true;
		Set<String> keys = this.configMap.keySet(); //Get keyset of MAP

		//Iterate over KeySet
		for (String s : keys) {
			AbstractConfig ac = this.configMap.get(s); //Look up AbstractConfig
			if (ac != null) {
				//If the .doConfig() method returns FALSE of FAILED, then set retVal = FALSE
				if (!ac.doConfig()) { 
					retVal = false;
				}
			} else {
				System.err.println("Null AbstractConfig: " + s);
				retVal = false;
			}
		}

		return retVal;
	}
}
