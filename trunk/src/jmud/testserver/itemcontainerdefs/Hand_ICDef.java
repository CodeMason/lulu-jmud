package jmud.testserver.itemcontainerdefs;

import jmud.engine.core.Targetable;
import jmud.engine.item.AbstractItemContainerDef;
import jmud.engine.item.Item;
import jmud.engine.item.ItemContainer;
import jmud.engine.stats.Stat;

/**
 * Extention of AbstractItemContainerDef.  Adds in particulars for a Hand
 * 
 * @author Dave Loman
 */
public class Hand_ICDef extends AbstractItemContainerDef {

	public Hand_ICDef(String name) {
		super(name);
	}



	@Override
	public boolean getCheck(Targetable targetGetting, Item i) {
		return true;
	}

	@Override
	public boolean putCheck(Targetable targetPutting, Item i) {
		return true;
	}

	@Override
	public boolean use(Targetable targetUsing, Item i) {
		return true;
	}



	@Override
	public boolean addCheck(Targetable targetAdding, ItemContainer ic, Item i) {
		
		//Capacity Check
		Stat s = ic.getStatMap().get("NumberOfItems");
		if ((s != null) && (s.getCurrent() == s.getMax())) {
			//ic is full
			//TODO Send "container is full" message to player
			return false;
		}
		
		//Already in there check
		if (ic.getItems().contains(i)) {
			//TODO Send "You cannot add that, its already in there!!!" message to player			
		}
		
		return true;
	}



	@Override
	public boolean remCheck(Targetable targetRemoving, ItemContainer ic, Item i) {
		//Capacity Check
		Stat s = ic.getStatMap().get("NumberOfItems");
		if (s.getCurrent() == s.getMin()) {
			//ic is empty
			//TODO Send "container is enpty" message to player
			return false;
		}
		
		//Already in there check
		if (!ic.getItems().contains(i)) {
			//TODO Send "You cannot remove that, its not in there!!!" message to player			
		}
		
		return true;
	}

}







