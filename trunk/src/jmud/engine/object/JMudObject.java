package jmud.engine.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import jmud.engine.attribute.Attribute;
import jmud.engine.behavior.Behavior;
import jmud.engine.event.JMudEventType;
import jmud.engine.event.JMudEvent;

/**
 * @author David Loman
 */
public class JMudObject {

   /**
    * UUID for 99.99999% assured ability to differentiate between any/all
    * JMudObjects.
    */
   private UUID uuid = null;

   /**
    * Name just for the sake of Human readability. Not required to be
    * implemented anywhere. Works well for testing though!
    */
   private String name = "";;

   /**
    * Reference to this object's parent JMudObject object. A null parent
    * indicates the ROOT JMudObject of the tree.
    */
   private JMudObject parent = null;

   /**
    * A HashMap that maps a JMudObject object's UUID to the reference to the
    * JMudObject.
    */
   private final Map<UUID, JMudObject> children = Collections
         .synchronizedMap(new HashMap<UUID, JMudObject>());

   /**
    * A HashMap that maps an Attribute object's string name to the reference to
    * the Attribute object.
    */
   private final Map<String, Attribute> attr = Collections
         .synchronizedMap(new HashMap<String, Attribute>());
   /**
    * Create a map of events to lists of Behaviors that handle the event With so
    * many possibilities for object behavior, making unique event handlers for
    * each JMudObject will become tedious; by having a list of discrete, atomic
    * behaviors, we can re-use them, e.g. Unlock, Open, Wait, Close, Lock, etc.
    */
   private final Map<JMudEventType, List<Behavior>> behaviors = Collections
         .synchronizedMap(new HashMap<JMudEventType, List<Behavior>>());

   /**
    * Default constructor.
    */
   public JMudObject() {
      this(UUID.randomUUID(), "", null);
   }

   public JMudObject(final JMudObject inParent) {
      this(UUID.randomUUID(), "", inParent);
   }

   public JMudObject(final String inName) {
      this(UUID.randomUUID(), inName, null);
   }

   public JMudObject(final String inName, final JMudObject inParent) {
      this(UUID.randomUUID(), inName, inParent);
   }

   private JMudObject(final UUID inUuid, final String inName,
         final JMudObject inParent) {
      super();
      this.parent = inParent;
      this.name = inName;
      this.uuid = inUuid;
   }

   /**
    * Register a behaviors with an event class.
    * @param b
    *           Behavior to mapped to Behavior.getEventTypesHandled();
    */
   public final void addEventBehavior(final Behavior b) {
      List<JMudEventType> ets = b.getEventTypesHandled();

      for (JMudEventType e : ets) {

         List<Behavior> behs = this.behaviors.get(e);

         if (behs == null) {
            // There was no mapping for EventType e, so make a newone
            behs = new ArrayList<Behavior>();
            behs.add(b);
            this.behaviors.put(e, behs);
         } else {
            // There was a mapping for EventType e
            behs.add(b);
         }
      }
   }

   /**
    * Remove all attributes.
    */
   public final void attributeClear() {
      attr.clear();
   }



   public final boolean attributeContainsKey(final String key) {
      return attr.containsKey(key);
   }

	/*
	 * Attribute HashMap Delegates
	 */

   public final boolean attributeContainsValue(final Attribute value) {
      return attr.containsValue(value);
   }

   public final Attribute attributeGet(final String key) {
      return attr.get(key);
   }

   public final Set<String> attributeKeySet() {
      return attr.keySet();
   }

   public final Attribute attributePut(final String key, final Attribute value) {
      return attr.put(key, value);
   }

   public final Attribute attributeRemove(final String key) {
      return attr.remove(key);
   }

   /**
    * @return the the number of elements in the attribute collection
    */
   public final int attributeSize() {
      return attr.size();
   }

   public final Collection<Attribute> attributeValues() {
      return attr.values();
   }

   public final void changeParent(final JMudObject newParent) {

      // remove ties to old parent
      this.orphan();

      // establish newParent's reference to this
      if (newParent != null) {
         newParent.childrenAdd(this);
      }
   }

   public final JMudObject childrenAdd(final JMudObject jmo) {
      jmo.setParent(this);
      return children.put(jmo.getUUID(), jmo);
   }

   /*
    * Children HashMap Delegates
    */

   public final void childrenClear() {
      children.clear();
   }

   public final boolean childrenContainsKey(final UUID uuid) {
      return children.containsKey(uuid);
   }

   public final boolean childrenContainsValue(final JMudObject jmo) {
      return children.containsValue(jmo);
   }

   public final JMudObject childrenGet(final String name) {
      for (JMudObject jmo : this.children.values()) {
         if (jmo.getName().equals(name)) {
            return jmo;
         }
      }
      return null;
   }

   public final JMudObject childrenGet(final UUID uuid) {
      return children.get(uuid);
   }

   public final Map<UUID, JMudObject> childrenGetAll() {
      return this.children;
   }

   public final Set<UUID> childrenKeySet() {
      return children.keySet();
   }


   public final JMudObject childrenRemove(final JMudObject jmo) {
      this.childrenRemove(jmo.getUUID());
      return jmo;
   }

   public final JMudObject childrenRemove(final UUID uuid) {
      JMudObject jmo = this.children.remove(uuid);
      if (jmo != null) {
         jmo.setParent(null);
      }
      return jmo;
   }

   public final int childrenSize() {
      return this.children.size();
   }

   public final Collection<JMudObject> childrenValues() {
      return this.children.values();
   }

   /**
    * For any event, return the list of applicable behaviors
    * @param event
    *           the event to find behaviors for
    * @return the behaviors that match the event
    */
   public final List<Behavior> getBehaviors(final JMudEvent event) {
      return this.getBehaviors(event.getEventType());
   }

   /*
    * Parent/Child shortcuts
    */

   public final List<Behavior> getBehaviors(final JMudEventType et) {
      return behaviors.get(et);
   }

   public final String getName() {
      return this.name;
   }

   public final JMudObject getParent() {
      return this.parent;
   }

   /*
    * Getter/Setters
    */

   public final Map<UUID, JMudObject> getSiblings() {

      Map<UUID, JMudObject> map = null;

      // the ONLY way you should ever have Zero siblings is if you are ROOT
      if (this.parent != null) {
         map = this.parent.childrenGetAll();
         map.remove(this.getUUID());
      }
      return map;
   }

   public final UUID getUUID() {
      return this.uuid;
   }

   public final void orphan() {
      JMudObject parent = this.getParent();

      if (parent != null) {
         // first, remove the parent's reference to the child
         parent.childrenRemove(this);
      }
      // then remove the child's reference to the parent
      this.setParent(null);

   }

   public final void setName(final String n) {
      this.name = n;
   }

   /**
    * Directly sets this object's parent JMudObject object reference. WARNING:
    * the JMudObject tree is double linked. Setting this object's parent without
    * removing this object from the parent's Children list will violate the
    * rules of a tree and create a Graph.... bad things will happen if this is
    * done!
    * @param newParent
    *           the new parent of this JMudObject
    */
   private void setParent(final JMudObject newParent) {
      this.parent = newParent;
   }

   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public final String toString() {
      return this.name + " \t(" + this.uuid.toString() + ")";
   }
}
