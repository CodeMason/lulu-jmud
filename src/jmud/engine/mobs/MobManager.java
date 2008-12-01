package jmud.engine.mobs;

import java.util.HashMap;

/**
 * Singleton patterned class Manages all Mob objects.
 * @author David Loman
 * @version 0.1
 */

public class MobManager {
   /**
    * MobManagerHolder is loaded on the first execution of
    * MobManager.getInstance() or the first access to MobManagerHolder.INSTANCE,
    * not before.
    */
   private static class MobManagerHolder {
      private static final MobManager INSTANCE = new MobManager();
   }

   public static MobManager getInstance() {
      return MobManagerHolder.INSTANCE;
   }

   private final HashMap<Integer, Mob> mobMap = new HashMap<Integer, Mob>();

   /*
    * Concrete Class Implementation
    */

   /*
    * Singleton Implementation
    */
   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor
    */
   protected MobManager() {
   }

   /*
    * Queue Access
    */
   public final void addMob(final Mob m) {
      synchronized (this.mobMap) {
         this.mobMap.put(m.getID(), m);
      }
   }

   public final Mob getMob(final int ID) {
      Mob r = null;
      synchronized (this.mobMap) {
         r = this.mobMap.get(ID);
      }
      return r;
   }

   public void init() {

   }

   public final void remMob(final Mob m) {
      synchronized (this.mobMap) {
         this.mobMap.remove(m.getID());
      }
   }

}
