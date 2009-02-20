package jmud.engine.item;

import jmud.engine.core.Targetable;
import jmud.engine.dbio.Persistable;
import jmud.engine.stats.StatMap;

/**
 * @author Chris Maguire
 */
public class Item implements Targetable, Persistable {
	private AbstractItemDef behavior;
	private final String name;

	private final StatMap stats = new StatMap();

	private final int uid;

	public Item(final String name, final int uid) {
		this.name = name;
		this.uid = uid;
	}

	public AbstractItemDef getBehavior() {
		return behavior;
	}

	public final String getName() {
		return name;
	}

	public final StatMap getStatMap() {
		return stats;
	}

	public final int getUid() {
		return uid;
	}

	public final void setBehavior(final AbstractItemDef behavior) {
		this.behavior = behavior;
	}

	@Override
	public boolean save() {
		// TODO Finish Item.Save()
		return false;
	}

}
