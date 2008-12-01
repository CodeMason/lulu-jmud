package jmud.engine.stats;

/**
 * Repository for all AbstractStatDef objects (and any subclass objects).
 * Allow for ease of addition, look up and removal.
 *
 * @author David Loman
 * @version 0.1
 */
import java.util.HashMap;
import java.util.HashSet;

import jmud.engine.core.Namespace;

public class StatDefRegistrar {
   /*
    * Singleton Implementation
    */

   /**
    * StatDefRegistrarHolder is loaded on the first execution of
    * StatDefRegistrar.getInstance() or the first access to
    * StatDefRegistrarHolder.INSTANCE, not before.
    */
   private static class StatDefRegistrarHolder {
      private static final StatDefRegistrar INSTANCE = new StatDefRegistrar();
   }

   public static StatDefRegistrar getInstance() {
      return StatDefRegistrarHolder.INSTANCE;
   }

   // StatDef name to Object reference mapping
   private final HashMap<String, AbstractStatDef> statDefNameToStatDefMap = new HashMap<String, AbstractStatDef>();

   /*
    * Static Class Implementation
    */

   // Namespace to Object reference Set mapping
   private final HashMap<Namespace, HashSet<AbstractStatDef>> namespaceToStatDefSetMap = new HashMap<Namespace, HashSet<AbstractStatDef>>();

   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor
    */
   protected StatDefRegistrar() {
   }

   public final void addStatDef(final AbstractStatDef asd) {
      // Add to the DefMap
      this.addStatDefToDefMap(asd);

      for (Namespace ns : asd.getNamespaces()) {
         this.addStatDefToSetMap(asd, ns);
      }

   }

   private void addStatDefToDefMap(final AbstractStatDef asd) {
      synchronized (this.statDefNameToStatDefMap) {
         this.statDefNameToStatDefMap.put(asd.getName(), asd);
      }
   }

   private void addStatDefToSetMap(final AbstractStatDef asd, final Namespace ns) {
      // Add to the SetMap
      HashSet<AbstractStatDef> hs = this.getAllDefsInNamespace(ns);

      // If the namespace isn't on the map yet, then make a new HasSet
      if (hs == null) {
         hs = new HashSet<AbstractStatDef>();

         // Add the reference to the AbstractStatDef to the Set
         synchronized (hs) {
            hs.add(asd);
         }

         // Now add the Set to the Map:
         synchronized (this.namespaceToStatDefSetMap) {
            this.namespaceToStatDefSetMap.put(ns, hs);
         }
      } else {
         // There already is a HashSet
         synchronized (hs) {
            hs.add(asd);
         }
      }
   }

   public final HashSet<AbstractStatDef> getAllDefsInNamespace(
         final Namespace ns) {
      HashSet<AbstractStatDef> out = null;
      synchronized (this.namespaceToStatDefSetMap) {
         out = this.namespaceToStatDefSetMap.get(ns);
      }
      return out;
   }

   public final AbstractStatDef getStatDef(final String name) {
      AbstractStatDef asdc = null;
      synchronized (this.statDefNameToStatDefMap) {
         asdc = this.statDefNameToStatDefMap.get(name);
      }
      return asdc;
   }

   public void init() {
   }

   public final AbstractStatDef remStatDef(final String name) {
      AbstractStatDef asd = this.remStatDefFromDefMap(name);

      for (Namespace ns : asd.getNamespaces()) {
         this.remStatDefFromSetMap(asd, ns);
      }

      return asd;
   }

   public final AbstractStatDef remStatDefFromDefMap(final String name) {
      AbstractStatDef asd = null;
      synchronized (this.statDefNameToStatDefMap) {
         asd = this.statDefNameToStatDefMap.remove(name);
      }
      return asd;

   }

   public final void remStatDefFromSetMap(final AbstractStatDef asd,
         final Namespace ns) {
      // get a handle on the HashSet
      HashSet<AbstractStatDef> hs = this.getAllDefsInNamespace(ns);

      // If the namespace isn't on the map yet, then no worries.
      if (hs != null) {
         // But if there is, pull out our asd from the set.
         synchronized (hs) {
            hs.remove(asd);
         }
      }

      return;
   }

}
