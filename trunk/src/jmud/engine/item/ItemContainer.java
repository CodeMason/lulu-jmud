package jmud.engine.item;

import java.util.ArrayList;

/**
 * Anything that can hold anything else.
 * @author Chris Maguire
 */
public class ItemContainer extends Item {
   private AbstractItemContainerDef behavior;

   private final ArrayList<Item> items = new ArrayList<Item>();

   public ItemContainer(final int condition, final String name, final int uid) {
      super(name, uid);
   }

   @Override
   public final AbstractItemContainerDef getBehavior() {
      return behavior;
   }

   public ArrayList<Item> getItems() {
      return items;
   }

   public void setBehavior(final AbstractItemContainerDef behavior) {
      this.behavior = behavior;
   }

}
