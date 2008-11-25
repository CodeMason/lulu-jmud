/*
 * Hand.java
 *
 * Created on December 9, 2007, 5:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jmud.testserver.slots;

import jmud.engine.item.AbstractItemDef;
import jmud.engine.slot.Slot;

import java.util.LinkedList;
import java.util.List;

/**
 * @author root
 */
public abstract class SingleSlot extends Slot {
    private static final int MAX_ITEMS = 1;
    private AbstractItemDef item;
    private List<Slot> slots;
    private String name;

    /**
     * Creates a new instance of Foot
     *
     * @param name name of the new slot
     */
    public SingleSlot(int Id, String name) {
        super(Id, name);
    }

    public int itemCount() {
        return (item == null ? 0 : 1);
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public boolean hasItem(AbstractItemDef item) {
        return this.item == item;
    }

    public boolean isFull() {
        return itemCount() == MAX_ITEMS;
    }

    public boolean isEmpty() {
        return !isFull();
    }

    public AbstractItemDef removeItem(AbstractItemDef item) {
        return item;
    }

    public final List<AbstractItemDef> getItems() {
        List<AbstractItemDef> items = new LinkedList<AbstractItemDef>();
        if(item != null) {
            items.add(item);
        }
        /*
        else{
            System.out.println("SingleSlot.getItems() - Empty item list being returned");
        }
        */
        return items;
    }

    public int maxItems() {
        return MAX_ITEMS;
    }

    public boolean addItem(AbstractItemDef item) {
        if(this.item == null) {
            this.item = item;
            return true;
        }
        return false;
    }

    public AbstractItemDef removeItem(String name) {
        if(item.getName().equals(name)) {
            return item;
        }
        return null;
    }

    /**
     * Default override
     *
     * @return false
     */
    public boolean isGrabber() {
        return false;
    }

}
