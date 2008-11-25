package jmud.engine.item;

import java.util.HashMap;
import jmud.engine.stats.Stat;

public class Item {
	private int uid;
	private String name;

	private HashMap<String, Stat> stats = new HashMap<String, Stat>();

	private AbstractItemDef behavior = null;

	public Item(String name, int uid) {
		super();
		this.name = name;
		this.uid = uid;
	}

	public int getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public AbstractItemDef getBehavior() {
		return behavior;
	}

	public void setBehavior(AbstractItemDef behavior) {
		this.behavior = behavior;
	}

	public HashMap<String, Stat> getStats() {
		return stats;
	}
}
