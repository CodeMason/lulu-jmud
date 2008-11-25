package jmud.engine.item;
/**
 * Represents a virtual container in the game, e.g. a backpack, pouch.  Also
 * represents 'slots' on a mob/character: finger, head, back, arm, chest, etc
 * 
 * Created on 25NOV08
 * 
 * @author Dave Loman
 * @version 0.1
 */
import java.util.List;

public abstract class AbstractContainerDef extends AbstractItemDef {

	private int max_bulk;
	private int min_bulk;

	//What itemTypes can be put into this container
	private List<ItemTypes> validContentTypes;

	public AbstractContainerDef(int condition, int cur_bulk, boolean isMagic, boolean isRemoveable, boolean isWearable,
			String name, int uid, int weight, int max_bulk, int min_bulk, List<ItemTypes> validContentTypes) {
		super(condition, cur_bulk, isMagic, isRemoveable, isWearable, name, uid, weight);
		this.max_bulk = max_bulk;
		this.min_bulk = min_bulk;
		this.validContentTypes = validContentTypes;
	}



}
