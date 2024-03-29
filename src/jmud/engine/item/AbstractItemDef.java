package jmud.engine.item;

import jmud.engine.core.Targetable;

/**
 * Represents a definition for the behavior of an item.
 */
public abstract class AbstractItemDef {

   protected int containerId = 0;
   protected String name = "";

   public AbstractItemDef(final String name) {
      super();
      this.name = name;
   }

   /**
    * Force dev to implement a check for when the object is picked up.
    * @param targetGetting
    * @param item
    * @return
    */
   protected abstract boolean getCheck(Targetable targetGetting, Item item);

   public final String getName() {
      return name;
   }

   /**
    * Force dev to implement a check for when the object is put down.
    * @param targetPutting
    * @param i
    * @return
    */
   protected abstract boolean putCheck(Targetable targetPutting, Item i);

   /**
    * Force dev to implement logic for when the object is 'used'.
    * @param targetUsing
    * @param i
    * @return
    */
   public abstract boolean use(Targetable targetUsing, Item i);

}
