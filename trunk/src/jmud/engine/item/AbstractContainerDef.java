package jmud.engine.item;

import jmud.engine.core.Targetable;

/**
 * Represents a definition for the behavior of a container in the game, e.g.
 * a backpack, pouch.  Also represents 'slots' on a mob/character: 
 * finger, head, back, arm, chest, etc or places in a room: floor, chestOfDrawers
 * 
 * Created on 25NOV08
 * 
 * @author Dave Loman
 * @version 0.1
 */


public abstract class AbstractContainerDef extends AbstractItemDef {


	public AbstractContainerDef(String name) {
		super(name);
	}

	//Force dev to implement a check for when the object is picked up.
	public abstract boolean addCheck(Targetable targetAdding, ItemContainer ic); 
	
	//Force dev to implement a check for when the object is put down.
	public abstract boolean remCheck(Targetable targetRemoving, ItemContainer ic);

}
