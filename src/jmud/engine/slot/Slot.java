/*
 * Slot.java
 *
 * Created on December 9, 2007, 4:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jmud.engine.slot;

import java.util.LinkedList;
import java.util.List;

import jmud.engine.item.AbstractItemDef;

/**
 * A slot is a spot on a player (or mob?) that can hold something.
 * @author Chris Maguire
 */
public abstract class Slot {
   public static final int MAX_BULK = 100;
   public static final int MIN_BULK = 0;
   private List<String> aliases;
   private int Id;
   private final String name;

   /**
    * Creates a new instance of <code>Slot</code>.
    * @param name
    *           the name for the new slot
    */
   public Slot(final int Id, final String name) {
      this(Id, name, new LinkedList<String>());
   }

   public Slot(final int Id, final String name, final List<String> aliases) {
      this.Id = Id;
      this.name = name;
      this.aliases = aliases;
   }

   public abstract boolean addItem(AbstractItemDef item);

   public final List<String> getAliases() {
      return aliases;
   }

   public final int getId() {
      return Id;
   }

   public abstract List<AbstractItemDef> getItems();

   public final String getName() {
      return name;
   }

   public abstract boolean hasItem(AbstractItemDef item);

   public abstract boolean isEmpty();

   public abstract boolean isFull();

   public abstract boolean isGrabber();

   public abstract int itemCount();

   public abstract int maxBulk();

   // public abstract List<Slot> getSlots();
   // why would slots need to "house" other slots?
   // For instance, a hand will semantically "house" fingers, but does it need
   // to logically?
   // Left hand doesn't need to have 'left index finger', the user will just
   // assume it.

   public abstract int maxItems();

   public abstract AbstractItemDef removeItem(String name);

   public final void setAliases(final List<String> aliases) {
      this.aliases = aliases;
   }

   public final void setId(final int id) {
      this.Id = id;
   }

}
