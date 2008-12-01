package jmud.engine.character;

/**
 *
 * 24NOV08:  Character objects should only represent the data that pertains to
 * an individual character... Attribute, Name, Description, etc
 * 
 * Created on 25NOV08
 */

import jmud.engine.core.Targetable;
import jmud.engine.dbio.Persistable;
import jmud.engine.item.ItemContainer;
import jmud.engine.stats.StatMap;

import java.util.HashMap;

public class Character implements Targetable, Persistable {
	private int charID;

	// info
	private String name;
	private String description;
	private String prompt;

	// stats
	private StatMap stats = new StatMap();

	//'Slots'
	private HashMap<String, ItemContainer> slots = new HashMap<String, ItemContainer>();

	// location
//	private Room room;


	public Character(int iID, String name, String desc) {
		this.charID = iID;
		this.name = name;
		this.description = desc;
	}

	public int getCharID() {
		return charID;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public StatMap getStatMap() {
		return stats;
	}


	public HashMap<String, ItemContainer> getSlots() {
		return slots;
	}


	public String getPrompt() {
		return prompt;
	}

}
