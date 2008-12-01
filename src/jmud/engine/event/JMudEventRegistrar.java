package jmud.engine.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jmud.engine.object.JMudObject;

/**
 * This serves as a registrar for JMudEventSubscription objects It is
 * synchronized in anyway as so far as the Maps and Lists are concerned.
 * Synchronization of the individual JMudEvent objects and JMudObject objects
 * must be done externally.
 * @author David Loman
 */

public class JMudEventRegistrar {
   /**
    * JobManagerHolder is loaded on the first execution of
    * JobManager.getInstance() or the first access to JobManagerHolder.INSTANCE,
    * not before.
    */
   private static class Holder {
      private static final JMudEventRegistrar INSTANCE = new JMudEventRegistrar();
   }

   public static JMudEventRegistrar getInstance() {
      return Holder.INSTANCE;
   }

   private final Map<UUID, JMudEventSubscription> uuidMap = Collections
         .synchronizedMap(new HashMap<UUID, JMudEventSubscription>());

   /*
    * Concrete Class Implementation
    */

   private final Map<JMudObject, List<JMudEventSubscription>> sourceMap = Collections
         .synchronizedMap(new HashMap<JMudObject, List<JMudEventSubscription>>());

   private final Map<JMudEventType, List<JMudEventSubscription>> eventMap = Collections
         .synchronizedMap(new HashMap<JMudEventType, List<JMudEventSubscription>>());

   private final Map<JMudObject, List<JMudEventSubscription>> targetMap = Collections
         .synchronizedMap(new HashMap<JMudObject, List<JMudEventSubscription>>());

   /*
    * Singleton Implementation
    */
   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor
    */
   protected JMudEventRegistrar() {
   }

   /*
    * Common Src/Tgt map IO
    */
   private void addToCommonMap(final JMudEventSubscription jmes,
         final Map<JMudObject, List<JMudEventSubscription>> map) {
      // first, get the ArrayList keyed to the JMudObject:
      List<JMudEventSubscription> al = map.get(jmes.getSource());

      // check to see if there was a mapping!
      if (al == null) {
         // if not then make a new ArrayList
         al = Collections
               .synchronizedList(new ArrayList<JMudEventSubscription>());

         // Now that we have a valid list, add in our incoming
         // JMudEventSubscription
         al.add(jmes);

         // Now map the List into the map by source:
         this.sourceMap.put(jmes.getSource(), al);
      } else {
         // we have a good ArrayList returned:
         al.add(jmes);
      }
   }

   /*
    * Event map IO
    */
   private void addToEventMap(final JMudEventSubscription jmes) {
      // first, get the ArrayList keyed to the JMudEventType:
      List<JMudEventSubscription> al = this.eventMap.get(jmes.getEventType());

      // check to see if there was a mapping!
      if (al == null) {
         // if not then make a new ArrayList
         al = Collections
               .synchronizedList(new ArrayList<JMudEventSubscription>());

         // Now that we have a valid list, add in our incoming
         // JMudEventSubscription
         al.add(jmes);

         // Now map the List into the map by source:
         this.eventMap.put(jmes.getEventType(), al);
      } else {
         // we have a good ArrayList returned:
         al.add(jmes);
      }
   }

   /*
    * Source map IO
    */
   private void addToSourceMap(final JMudEventSubscription jmes) {
      this.addToCommonMap(jmes, this.sourceMap);
   }

   /*
    * Target map IO
    */
   private void addToTargetMap(final JMudEventSubscription jmes) {
      this.addToCommonMap(jmes, this.targetMap);
   }

   /*
    * UUID map IO
    */
   private void addToUUIDMap(final JMudEventSubscription jmes) {
      this.uuidMap.put(jmes.getSubscriptionID(), jmes);
   }

   public final JMudEventSubscription getSubscriptionByUUID(final UUID uuid) {
      return this.uuidMap.get(uuid);
   }

   public final List<JMudEventSubscription> getSubscriptionsByEvent(
         final JMudEvent jme) {
      return this.eventMap.get(jme);
   }

   public final List<JMudEventSubscription> getSubscriptionsBySource(
         final JMudObject jmo) {
      return this.sourceMap.get(jmo);
   }

   public final List<JMudEventSubscription> getSubscriptionsByTarget(
         final JMudObject jmo) {
      return this.targetMap.get(jmo);
   }

   /*
    * Subscription IO
    */
   public final void registerSubscription(final JMudEventSubscription jmes) {
      // Add to uuidMap:
      this.addToUUIDMap(jmes);

      // Add to targetMap:
      this.addToTargetMap(jmes);

      // Add to eventMap:
      this.addToEventMap(jmes);

      // Add to sourceMap:
      this.addToSourceMap(jmes);

   }

   private boolean remFromCommonMap(final JMudEventSubscription jmes,
         final Map<JMudObject, List<JMudEventSubscription>> map) {
      // first, get the ArrayList keyed to the source JMudObject:
      List<JMudEventSubscription> al = map.get(jmes.getSource());

      // check to see if there was a mapping!
      if (al == null) {
         // if not then looks like we are all good to go.
         return true;

      } else {
         // we have a good ArrayList returned:
         return al.remove(jmes);
      }
   }

   private boolean remFromEventMap(final JMudEventSubscription jmes) {
      // first, get the ArrayList keyed to the source JMudObject:
      List<JMudEventSubscription> al = this.eventMap.get(jmes.getEventType());

      // check to see if there was a mapping!
      if (al == null) {
         // if not then looks like we are all good to go.
         return true;

      } else {
         // we have a good ArrayList returned:
         return al.remove(jmes);
      }
   }

   private boolean remFromSourceMap(final JMudEventSubscription jmes) {
      return this.remFromCommonMap(jmes, this.sourceMap);
   }

   private boolean remFromTargetMap(final JMudEventSubscription jmes) {
      return this.remFromCommonMap(jmes, this.targetMap);
   }

   private JMudEventSubscription remFromUUIDMap(final JMudEventSubscription jmes) {
      return this.remFromUUIDMap(jmes.getSubscriptionID());
   }

   private JMudEventSubscription remFromUUIDMap(final UUID uuid) {
      return this.uuidMap.remove(uuid);
   }

   public final void unregisterSubscription(final JMudEventSubscription jmes) {
      // Remove from uuidMap:
      this.remFromUUIDMap(jmes);

      // Remove from targetMap:
      this.remFromTargetMap(jmes);

      // Remove from eventMap:
      this.remFromEventMap(jmes);

      // Remove from sourceMap:
      this.remFromSourceMap(jmes);
   }

}
