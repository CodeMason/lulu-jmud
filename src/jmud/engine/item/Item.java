package jmud.engine.item;

import jmud.engine.core.Targetable;
import jmud.engine.dbio.Persistable;
import jmud.engine.stats.StatMap;

public class Item implements Targetable, Persistable {
	private int uid;
	private String name;

	private StatMap stats = new StatMap();

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

	public StatMap getStatMap() {
		return stats;
	}

}
