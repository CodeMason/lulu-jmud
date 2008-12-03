package jmud.engine.object;

import jmud.engine.attribute.Attribute;
import jmud.engine.behavior.Behavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;

import java.util.*;

/**
 * @author David Loman
 */
public class JMudObject {

	/**
	 * UUID for 99.99999% assured ability to differentiate between any/all
	 * JMudObjects.
     *
     * CM: isn't there a more accurate implementation? I mean, why settle for 2nd best right?
	 */
	private UUID uuid;

	/**
	 * Name just for the sake of Human readability. Not required to be
	 * implemented anywhere. Works well for testing though!
	 */
	private String name = "";

	/**
	 * Reference to this object's parent JMudObject object. A null parent
	 * indicates the ROOT JMudObject of the tree.
	 */
	private JMudObject parent;

	/**
	 * A HashMap that maps a JMudObject object's UUID to the reference to the
	 * JMudObject.
	 */
	private final Map<UUID, JMudObject> children = Collections.synchronizedMap(new HashMap<UUID, JMudObject>());

	/**
	 * A HashMap that maps an Attribute object's string name to the reference to
	 * the Attribute object.
	 */
	private final Map<String, Attribute> attr = Collections.synchronizedMap(new HashMap<String, Attribute>());
	/**
	 * Create a map of events to lists of Behaviors that handle the event With
	 * so many possibilities for object behavior, making unique event handlers
	 * for each JMudObject will become tedious; by having a list of discrete,
	 * atomic behaviors, we can re-use them, e.g. Unlock, Open, Wait, Close,
	 * Lock, etc.
	 */
	private final Map<JMudEventType, List<Behavior>> behaviors = Collections.synchronizedMap(new EnumMap<JMudEventType, List<Behavior>>(JMudEventType.class));

    private final Map<JMudObject, Map<JMudEventType, List<Behavior>>> nonTargetBehaviors = Collections.synchronizedMap(new HashMap<JMudObject, Map<JMudEventType, List<Behavior>>>());

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

	private JMudObject(final UUID inUuid, final String inName, final JMudObject inParent) {
		this.parent = inParent;
		this.name = inName;
		this.uuid = inUuid;

		//this.addEventBehavior(new BaseJMudObjectBehavior());
    }

	/**
	 * Register a behaviors with an event class.
	 *
	 * @param b Behavior to mapped to Behavior.getEventTypesHandled();
	 */
	public final void addEventBehavior(final Behavior b) {
		List<JMudEventType> ets = b.getEventTypesHandled();

		for (JMudEventType e : ets) {

			List<Behavior> behs = this.behaviors.get(e);

			if (behs == null) {
				// There was no mapping for EventType e, so make a new one
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
     * Register a behavior with a particular target and event type
     *
     * This allows us to register a behavior that can handle any type, which
     * will be necessary for subscriptions (at least the way I prototyped it)
     * I didn't want to have to add an "any" event type, although that is another option.
     *
     * object X can say: if event Z happens to object Y, run behavior Q
     * Q doesn't have to be a "Z Behavior" because Q isn't handling Z, Q is simply in response to Z
     *
     * @param eventType the event type to register the behavior with
     * @param behavior the behavior to run in response to the event
     */
    public void addEventBehavior(JMudEventType eventType, Behavior behavior, JMudObject target){
        List<Behavior> behaviors;
        Map<JMudEventType, List<Behavior>> eventBehaviors = this.nonTargetBehaviors.get(target);

        if(eventBehaviors == null){
            // There was no mapping for EventType e, so make a new one
            eventBehaviors = new EnumMap<JMudEventType, List<Behavior>>(JMudEventType.class);
        }

        if((behaviors = eventBehaviors.get(eventType)) == null){
            behaviors = new ArrayList<Behavior>();
            eventBehaviors.put(eventType, behaviors);
        }

        behaviors.add(behavior);
        nonTargetBehaviors.put(target, eventBehaviors);
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
		return this.children.put(jmo.getUUID(), jmo);
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

    // ToDo CM: we may want to look at optimizing this (maybe name -> uuid hash?)
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

//		System.err.println("\n Removing " + jmo.toStringShort() + " from " + this.toStringShort() + "\n");

        // ToDo CM: interesting :)
        if ("pcSteve".equals(this.name)) {
			System.out.println();
		}
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
	 *
	 * @param event
	 *            the event to find behaviors for
	 * @return the behaviors that match the event
	 */
	public final List<Behavior> getBehaviors(final JMudEvent event) {
		return this.getBehaviors(event.getEventType());
	}

	public final List<Behavior> getBehaviors(final JMudEventType et) {
		return behaviors.get(et);
	}

    /**
     * Ok, this is starting to get out of hand, but it's 12:38am and I'm
     * determined to at least get a trigger event to work!
     *
     * What this does is return all the behaviors that respond to events where this
     * object isn't the target: this means that this object can respond to events that it has
     * subscribed to from other objects differently than it responds to events where it is the target.
     * e.g. I might respond different if you grab my wallet than if you grab me. I'll throw a trigger
     * on my wallet's getEvent and respond to it by shooting you. If you get me, I might respond differently.
     *
     * I really don't think their should be two sets of behaviors, necessarily, but I'll sort it out later. Details, details ...
     *
     * @param eventType the event type to retrieve the behavior for
     * @return the is-not-the-target behaviors (i.e where this object isn't the target) for this event
     */
    public final List<Behavior> getNonTargetBehaviors(JMudObject target, final JMudEventType eventType){
        Map<JMudEventType, List<Behavior>> eventTypeBehaviors = nonTargetBehaviors.get(target);

        if(eventTypeBehaviors == null){
            return null;
        }

        // may be null
        return eventTypeBehaviors.get(eventType);

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
        // ToDo CM You mean the only way you won't have a parent? (I could be the only thing in a room, the only thing in a bag, the only brain cell in Dave's head (KIDDING!), etc.)
        if (this.parent != null) {
			map = this.parent.childrenGetAll();
            // filter out the calling object
            map.remove(this.getUUID());
        }
		return map;
	}

	public final UUID getUUID() {
		return this.uuid;
	}

	public final void orphan() {
		//System.err.println("\nOrphaning " + this.toStringShort() + "\n");

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
	 * the JMudObject tree is double linked. Setting this object's parent
	 * without removing this object from the parent's Children list will violate
	 * the rules of a tree and create a Graph.... bad things will happen if this
	 * is done!
     *
     * ToDo CM: hmmm, should we have a switchParent function that does both? (or do we already?)
	 *
	 * @param newParent the new parent of this JMudObject
	 */
	private void setParent(final JMudObject newParent) {

//		if (newParent == null) {
//			System.err.println("\n setParent for " + this.toStringShort() + " to NULL \n");
//		} else {
//			System.err.println("\n setParent for " + this.toStringShort() +
//					" to " + newParent.toStringShort() + " \n");
//		}

		this.parent = newParent;


	}

	@Override
	public final String toString() {
		StringBuilder out = new StringBuilder(this.toStringShort());

		if (this.parent != null) {
			out.append("\t hasParent:TRUE");
		} else {
			out.append("\t hasParent:FALSE");
		}

        out.append("\t childrenCount:")
           .append(this.childrenSize())
           .append("\t attrCount:")
           .append(this.attributeSize())
           .append("\t behaviorCount:")
           .append(this.behaviors.size());

		return out.toString();
	}
	public final String toStringShort() {
        return "JMudObject: " + this.name + "(" + this.uuid.toString() + ")";
	}

//TODO Make behavior delegates look like attribute and children delegates.
// CM: what are behavior delegates?

}
