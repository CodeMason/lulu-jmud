package jmud.engine.item;

import java.util.HashMap;

/**
 * Singleton patterned class manages all Mob objects.
 * @author David Loman
 * @version 0.1
 */
public class ItemManager {
   /**
    * <code>ItemManagerHolder</code> is loaded on the first execution of
    * <code>ItemManager.getInstance()</code> or the first access to
    * <code>ItemManagerHolder.INSTANCE</code>, not before.
    */
   private static class ItemManagerHolder {
      private static final ItemManager INSTANCE = new ItemManager();

      /**
       * <code>ItemManagerHolder</code> is a utility class. Disallowing
       * public/default constructor.
       */
      private ItemManagerHolder() {
      }
   }

   /**
    * @return the singleton <code>ItemManager</code> instance
    */
   public static ItemManager getInstance() {
      return ItemManagerHolder.INSTANCE;
   }

   private final HashMap<String, AbstractItemDef> ItemMap = new HashMap<String, AbstractItemDef>();

   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor.
    */
   protected ItemManager() {
   }

   public final void addItem(final AbstractItemDef item) {
      synchronized (this.ItemMap) {
         this.ItemMap.put(item.getName(), item);
      }
   }

   public final AbstractItemDef getItem(final String name) {
      AbstractItemDef r;
      synchronized (this.ItemMap) {
         r = this.ItemMap.get(name);
      }
      return r;
   }

   public final void remItem(final AbstractItemDef m) {
      synchronized (this.ItemMap) {
         this.ItemMap.remove(m.getName());
      }
   }

}
