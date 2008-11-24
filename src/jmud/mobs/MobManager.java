package jmud.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;



/**
 * Singleton patterned class
 * Manages all Mob objects.
 * 
 * @author David Loman
 * @version 0.1
 */

public class MobManager {
	/* 
	 * ********************************************
	 * Singleton Implementation
	 * ********************************************
	 */	
	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected MobManager() {
	}

	/**
	 * MobManagerHolder is loaded on the first execution of
	 * MobManager.getInstance() or the first access to
	 * MobManagerHolder.INSTANCE, not before.
	 */
	private static class MobManagerHolder {
		private final static MobManager INSTANCE = new MobManager();
	}

	public static MobManager getInstance() {
		return MobManagerHolder.INSTANCE;
	}

	/* 
	 * ********************************************
	 * Concrete Class Implementation
	 * ********************************************
	 */	

	private HashMap<Integer, Mob> mobMap = new HashMap<Integer, Mob>();

	
	public void init () {

	}
	
	/* 
	 * ********************************************
	 * Queue Access
	 * ********************************************
	 */	
	public void addMob(Mob m) {
		synchronized (this.mobMap) {
			this.mobMap.put(m.getID(), m);
		}		
	}

	public Mob getMob(int ID) {
		Mob r = null;
		synchronized (this.mobMap) {
			r = this.mobMap.get(ID);
		}
		return r;
	}

	public void remMob(Mob m) {
		synchronized (this.mobMap) {
			this.mobMap.remove(m.getID());
		}		
	}	
	
}

















