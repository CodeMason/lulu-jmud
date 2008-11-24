package jmud.rooms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;



/**
 * Singleton patterned class
 * Manages all Room objects.
 * 
 * @author David Loman
 * @version 0.1
 */

public class RoomManager {
	/* 
	 * ********************************************
	 * Singleton Implementation
	 * ********************************************
	 */	
	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected RoomManager() {
	}

	/**
	 * RoomManagerHolder is loaded on the first execution of
	 * RoomManager.getInstance() or the first access to
	 * RoomManagerHolder.INSTANCE, not before.
	 */
	private static class RoomManagerHolder {
		private final static RoomManager INSTANCE = new RoomManager();
	}

	public static RoomManager getInstance() {
		return RoomManagerHolder.INSTANCE;
	}

	/* 
	 * ********************************************
	 * Concrete Class Implementation
	 * ********************************************
	 */	

	private HashMap<Integer, Room> roomMap = new HashMap<Integer, Room>();
	private HashMap<Room, ArrayList<Character>> roomCharMap = new HashMap<Room, ArrayList<Character>>();

	
	public void init () {

	}
	
	/* 
	 * ********************************************
	 * Queue Access
	 * ********************************************
	 */	
	public void addRoom(Room r) {
		synchronized (this.roomMap) {
			this.roomMap.put(r.getID(), r);
		}		
	}

	public Room getRoom(int ID) {
		Room r = null;
		synchronized (this.roomMap) {
			r = this.roomMap.get(ID);
		}
		return r;
	}

	public void remRoom(Room r) {
		synchronized (this.roomMap) {
			this.roomMap.remove(r.getID());
		}		
	}	
	
}

















