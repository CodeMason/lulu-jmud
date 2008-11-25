package jmud.engine.item;

import java.util.HashMap;

import jmud.engine.stats.AbstractStatDef;

public class Item {
	private int uid;
	private String name;

	public static HashMap<String, AbstractStatDef> statsDefs = new HashMap<String, AbstractStatDef>();

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
}
