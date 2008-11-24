package jmud.item;

import java.util.HashMap;



/**
 * Singleton patterned class
 * Manages all Mob objects.
 *
 * @author David Loman
 * @version 0.1
 */

public class ItemManager {
	/*
	 * ********************************************
	 * Singleton Implementation
	 * ********************************************
	 */
	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected ItemManager() {
	}

	/**
	 * ItemManagerHolder is loaded on the first execution of
	 * ItemManager.getInstance() or the first access to
	 * ItemManagerHolder.INSTANCE, not before.
	 */
	private static class ItemManagerHolder {
		private static final ItemManager INSTANCE = new ItemManager();
	}

	public static ItemManager getInstance() {
		return ItemManagerHolder.INSTANCE;
	}

	/*
	 * ********************************************
	 * Concrete Class Implementation
	 * ********************************************
	 */

	private final HashMap<Integer, Item> ItemMap = new HashMap<Integer, Item>();


	public void init () {

	}

	/*
	 * ********************************************
	 * Queue Access
	 * ********************************************
	 */
	public void addItem(Item item) {
		synchronized (this.ItemMap) {
			this.ItemMap.put(item.getId(), item);
		}
	}

	public Item getItem(int ID) {
		Item r;
		synchronized (this.ItemMap) {
			r = this.ItemMap.get(ID);
		}
		return r;
	}

	public void remItem(Item m) {
		synchronized (this.ItemMap) {
			this.ItemMap.remove(m.getId());
		}
	}

}

















