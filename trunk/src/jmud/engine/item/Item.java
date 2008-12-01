package jmud.engine.item;

import jmud.engine.core.Targetable;
import jmud.engine.dbio.Persistable;
import jmud.engine.stats.StatMap;

public class Item implements Targetable, Persistable {
   private final int uid;
   private final String name;

   private final StatMap stats = new StatMap();

   private AbstractItemDef behavior;

   public Item(final String name, final int uid) {
      this.name = name;
      this.uid = uid;
   }

   public AbstractItemDef getBehavior() {
      return behavior;
   }

   public final String getName() {
      return name;
   }

   public final StatMap getStatMap() {
      return stats;
   }

   public final int getUid() {
      return uid;
   }

   public final void setBehavior(final AbstractItemDef behavior) {
      this.behavior = behavior;
   }

}
