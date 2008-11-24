package jmud.character.statmods;

import java.util.HashMap;

public class StatModDefRegistrar {
	/* 
	 * ********************************************
	 * Static Class Implementation
	 * ********************************************
	 */	
	
	private HashMap<String, StatModDef> statModMap = new HashMap<String, StatModDef>();
	
	
	public void init () {

	}
	
	public void addStatDef(String name, StatModDef cmd) {
		synchronized (this.statModMap) {
			this.statModMap.put(name, cmd);
		}
	}
	
	public StatModDef getStatDef(String name) {
		StatModDef c = null;
		synchronized (this.statModMap) {
			c = this.statModMap.get(name);
		}
		return c;
	}
	
	public StatModDef remStatDef(String name) {
		StatModDef c = null;
		synchronized (this.statModMap) {
			c = this.statModMap.remove(name);
		}
		return c;
	}
	
}

















