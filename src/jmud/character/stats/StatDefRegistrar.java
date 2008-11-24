package jmud.character.stats;

import java.util.HashMap;

public class StatDefRegistrar {
	/* 
	 * ********************************************
	 * Static Class Implementation
	 * ********************************************
	 */	
	
	private HashMap<String, StatDef> statDefMap = new HashMap<String, StatDef>();
	
	
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

















