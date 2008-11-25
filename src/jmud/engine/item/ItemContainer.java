package jmud.engine.item;

import java.util.List;

public class ItemContainer extends Item {
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
