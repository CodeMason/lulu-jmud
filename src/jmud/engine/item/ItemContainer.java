package jmud.engine.item;

import java.util.ArrayList;

public class ItemContainer extends Item {
   private AbstractItemContainerDef behavior;

   private final ArrayList<Item> items = new ArrayList<Item>();

   public ItemContainer(int condition, String name, int uid) {
      super(name, uid);
   }

   @Override
   public final AbstractItemContainerDef getBehavior() {
      return behavior;
   }

   public ArrayList<Item> getItems() {
      return items;
   }

   public void setBehavior(AbstractItemContainerDef behavior) {
      this.behavior = behavior;
   }

}
