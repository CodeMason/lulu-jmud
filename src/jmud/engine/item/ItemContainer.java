package jmud.engine.item;

import java.util.HashMap;
import java.util.List;

import jmud.engine.stats.AbstractStatDef;

public class ItemContainer extends Item {
	public static HashMap<String, AbstractStatDef> statsDefs = new HashMap<String, AbstractStatDef>();

	// What itemTypes can be put into this container
	private List<ItemTypes> validContentTypes;

	private AbstractContainerDef behavior = null;

	public ItemContainer(int condition, String name, int uid, List<ItemTypes> validContentTypes) {
		super(name, uid);

		this.validContentTypes = validContentTypes;
	}

	public AbstractContainerDef getBehavior() {
		return behavior;
	}

	public void setBehavior(AbstractContainerDef behavior) {
		this.behavior = behavior;
	}

	public List<ItemTypes> getValidContentTypes() {
		return validContentTypes;
	}

}
