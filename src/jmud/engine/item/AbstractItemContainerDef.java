package jmud.engine.item;

import jmud.engine.core.Targetable;

/**
 * Represents a definition for the behavior of a container in the game, e.g. a
 * backpack, pouch. Also represents 'slots' on a mob/character: finger, head,
 * back, arm, chest, etc or places in a room: floor, chestOfDrawers
 * 
 * Created on 25NOV08
 * 
 * @author Dave Loman
 * @version 0.1
 */

public abstract class AbstractItemContainerDef extends AbstractItemDef {

	public AbstractItemContainerDef(String name) {
		super(name);
	}

	/*
	 * Check to perform when an attempt is made to place i into ic.
	 */
	protected abstract boolean addCheck(Targetable targetAdding, ItemContainer ic, Item i);

	public boolean add(Targetable targetAdding, ItemContainer ic, Item i) {
		// perform a specific check
		boolean retVal = this.addCheck(targetAdding, ic, i);

		// if the check is good, then add i to ic.
		if (retVal) {
			ic.getItems().add(i);
		}

		return retVal;
	}

	/*
	 * Check to perform when an attempt is made to remove i from ic.
	 */
	protected abstract boolean remCheck(Targetable targetRemoving, ItemContainer ic, Item i);

	public boolean remove(Targetable targetAdding, ItemContainer ic, Item i) {
		// perform a specific check
		boolean retVal = this.remCheck(targetAdding, ic, i);

		// if the check is good, then remove i from ic.
		if (retVal) {
			ic.getItems().remove(i);
		}

		return retVal;
	}

}
