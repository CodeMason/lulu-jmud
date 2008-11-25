package jmud.engine.item;

import jmud.engine.core.Targetable;



/**
 * Represents a virtual item in the game, e.g. a weapon, a piece of money, food,
 * etc.
 * 
 * Created on April 28, 2002, 8:53 AM
 * Modified: 25NOV08 Dave Loman
 * 
 * @author Chris Maguire
 * @version 0.1
 */
public abstract class AbstractItemDef {

	//TODO ultimately, it would me neat to replace these fixed fields with 'Stats' also... it would allow for item enchantments :)
	
	private int uid;
	private String name;
	private int weight; // total weight of the item
	private int cur_bulk;
	private int condition; // percentage of "mint" condition or % full.

	private boolean isRemoveable = false;
	private boolean isWearable = false;
	private boolean isMagic = false;

	public AbstractItemDef(int condition, int cur_bulk, boolean isMagic, boolean isRemoveable, boolean isWearable,
			String name, int uid, int weight) {
		super();
		this.condition = condition;
		this.cur_bulk = cur_bulk;
		this.isMagic = isMagic;
		this.isRemoveable = isRemoveable;
		this.isWearable = isWearable;
		this.name = name;
		this.uid = uid;
		this.weight = weight;
	}

	public int getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}

	public int getCur_bulk() {
		return cur_bulk;
	}

	public int getCondition() {
		return condition;
	}

	public boolean isRemoveable() {
		return isRemoveable;
	}

	public boolean isWearable() {
		return isWearable;
	}

	public boolean isMagic() {
		return isMagic;
	}

	//Force dev to implement logic for when the object is 'used'
	public abstract boolean use(Targetable targetUsing);
	
	//Force dev to implement a check for when the object is picked up.
	public abstract boolean getCheck(Targetable targetGetting); 
	
	//Force dev to implement a check for when the object is put down.
	public abstract boolean putCheck(Targetable targetPutting);
}
