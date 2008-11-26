package jmud.engine.item;

import java.util.ArrayList;

public class ItemContainer extends Item {
	private AbstractItemContainerDef behavior;

	private ArrayList<Item> items = new ArrayList<Item>();

	public ItemContainer(int condition, String name, int uid) {
		super(name, uid);
	}

	public AbstractItemContainerDef getBehavior() {
		return behavior;
	}

	public void setBehavior(AbstractItemContainerDef behavior) {
		this.behavior = behavior;
	}

	public ArrayList<Item> getItems() {
		return items;
	}



}
