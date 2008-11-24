package jmud.character;

/**
 * 
 * 24NOV08:  PlayerAccount should only represent the data that pertains to
 * Account only, aka, username, email and password plus any settings they
 * might have, aka Telnet settings.
 * 
 * A playerAccount will point to many Characters.
 * 
 * 
 * 
 *
 * Created on April 21, 2002, 4:24 PM
 */

import jmud.character.statmods.StatMod;
import jmud.character.stats.Stat;
import jmud.rooms.Room;
import jmud.slot.Slot;
import java.util.HashMap;

public class Character {

	private int charID;

	// settings
	public boolean debug;
	public boolean autoLook = true; // should the player automatically look when
									// entering a new room?

	// info
	private String name;
	private String description;


	// stats
	private HashMap<String, Stat> stats = new HashMap<String, Stat>();

	// statMods
	private HashMap<String, StatMod> statMods = new HashMap<String, StatMod>();

	// location
	private Room room;

	private HashMap<String, Slot> slots = new HashMap<String, Slot>();

	public Character(int iID, String name, String desc) {
		this.charID = iID;
		this.name = name;
		this.description = desc;
	}

	public int getCharID() {
		return charID;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean isAutoLook() {
		return autoLook;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public HashMap<String, Stat> getStats() {
		return stats;
	}

	public HashMap<String, StatMod> getStatMods() {
		return statMods;
	}

	public Room getRoom() {
		return room;
	}

	public HashMap<String, Slot> getSlots() {
		return slots;
	}

}
