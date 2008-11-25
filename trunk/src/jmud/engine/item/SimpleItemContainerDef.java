package jmud.engine.item;

import jmud.engine.core.Targetable;

public class SimpleItemContainerDef extends AbstractItemContainerDef{

	public SimpleItemContainerDef(String name) {
		super(name);
	}


	@Override
	protected boolean getCheck(Targetable targetGetting, Item it) {
		return true;
	}

	@Override
	protected boolean putCheck(Targetable targetPutting, Item it) {
		return true;
	}

	@Override
	public boolean use(Targetable targetUsing, Item it) {
		return true;
	}


	@Override
	protected boolean addCheck(Targetable targetAdding, ItemContainer ic, Item i) {
		return true;
	}


	@Override
	protected boolean remCheck(Targetable targetRemoving, ItemContainer ic, Item i) {
		return true;
	}
}
