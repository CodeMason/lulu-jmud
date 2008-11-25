package jmud.engine.item;

import jmud.engine.core.Targetable;
import jmud.engine.stats.Stat;

/**
 * Represents a definition for the behavior of an item.
 * 
 * Created on April 28, 2002, 8:53 AM Modified: 25NOV08 Dave Loman
 * 
 * @author Chris Maguire
 * @version 0.1
 */
public abstract class AbstractItemDef {

	protected int ContainerID = 0;
	protected String name = "";

	public AbstractItemDef(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	// Force dev to implement logic for when the object is 'used'
	public abstract boolean use(Targetable targetUsing, Item i);

	// Force dev to implement a check for when the object is picked up.
	protected abstract boolean getCheck(Targetable targetGetting, Item i);

	// Force dev to implement a check for when the object is put down.
	protected abstract boolean putCheck(Targetable targetPutting, Item i);


}
