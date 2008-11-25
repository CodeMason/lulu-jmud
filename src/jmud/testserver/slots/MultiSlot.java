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
public abstract class MultiSlot extends Slot {
    public static final int MAX_BULK = 10;
    public static final int MAX_ITEMS = 2;
    private List<AbstractItemDef> items;
    private String name;

    /**
     * Creates a new instance of Finger
     *
     * @param name name of the slot
     */
    public MultiSlot(int Id, String name) {
        super(Id, name);
        items = new LinkedList<AbstractItemDef>();
    }

    public final int itemCount() {
        return items.size();
    }

    public boolean isFull() {
        return itemCount() == MAX_ITEMS;
    }

    public boolean isEmpty() {
        return itemCount() == 0;
    }

    public final boolean hasItem(AbstractItemDef item) {
        return items.contains(item);
    }

    public final boolean addItem(AbstractItemDef item) {
        if(items.size() < MAX_ITEMS) {
            items.add(item);
            return true;
        }
        return false;
    }

    /**
     * Go through all the items and return the first one that matches the name
     */
    public final AbstractItemDef removeItem(String name) {
        for(AbstractItemDef i : items) {
            if(i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    public final List<AbstractItemDef> getItems() {
        return items;
    }

    public boolean isGrabber() {
        return false;
    }

}
