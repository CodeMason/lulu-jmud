package jmud.engine.object;

import jmud.engine.attribute.Attribute;
import jmud.engine.behavior.Behavior;
import jmud.engine.behavior.SendToConsoleBehavior;
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
	 * CM: isn't there a more accurate implementation? I mean, why settle for
	 * 2nd best right?
	 */
	// Always initialize non-final's, even if its to null;
	private UUID uuid = null;

	/**
	 * Name just for the sake of Human readability. Not required to be
	 * implemented anywhere. Works well for testing though!
	 */
	private String name = "";

	/**
	 * Reference to this object's parent JMudObject object. A null parent
	 * indicates the ROOT JMudObject of the tree.
	 */
	private JMudObject parent = null;

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
	private final Map<JMudEventType, List<Behavior>> behaviors = Collections
			.synchronizedMap(new EnumMap<JMudEventType, List<Behavior>>(JMudEventType.class));

	/*                         */
	/*                         */
	/* Constructors */
	/*                         */
	/*                         */

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

		// Default behaviors ALL JMudObjects will have.
		this.addEventBehavior(new SendToConsoleBehavior(this));
	}

	/*                         */
	/*                         */
	/* Behavior Tools */
	/*                         */
	/*                         */

	/**
	 * Register a behaviors with an event class.
	 *
	 * @param b
	 *            Behavior to mapped to Behavior.getEventTypesHandled();
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

	/*
	 * Children HashMap Delegates
	 */

	public final JMudObject childrenAdd(final JMudObject jmo) {
		jmo.setParent(this);
		return this.children.put(jmo.getUuid(), jmo);
	}

	public final void childrenClear() {
		children.clear();
	}

	public final boolean childrenContainsKey(final UUID uuid) {
		return children.containsKey(uuid);
	}

	public final boolean childrenContainsValue(final JMudObject jmo) {
		return children.containsValue(jmo);
	}

	// TODO CM: we may want to look at optimizing this (maybe name -> uuid
	// hash?)
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
		this.childrenRemove(jmo.getUuid());
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

	/*
	 * Getter/Setters
	 */

	public final String getName() {
		return this.name;
	}

	public final void setName(final String n) {
		this.name = n;
	}

	public final UUID getUuid() {
		return this.uuid;
	}

	/**
	 * Get a handle on the Attribute Map
	 *
	 * @return
	 */
	public Map<String, Attribute> getAttr() {
		return attr;
	}

	/*                         */
	/*                         */
	/* Hierarchy Tools */
	/*                         */
	/*                         */

	public final Map<UUID, JMudObject> getSiblings() {

		Map<UUID, JMudObject> map = null;

		// the ONLY way you should ever have Zero siblings is if you are ROOT
		// QQQ CM You mean the only way you won't have a parent? (I could be the
		// only thing in a room, the only thing in a bag, the only brain cell in
		// Dave's head (KIDDING!), etc.)
		// AAA DHL: Root, by definition will have no siblings nor will it have a
		// parent. (ignores the brain cell thing :P)
        // QQQ CM: It's just that below, you're not referring to siblings, you're
        // referring to the parent. :)

        if (this.parent != null) {
			map = this.parent.childrenGetAll();
			// filter out the calling object
			map.remove(this.getUuid());
		}
		return map;
	}

	public final void orphan() {
		// System.err.println("\nOrphaning " + this.toStringShort() + "\n");

		JMudObject parent = this.getParent();

		if (parent != null) {
			// first, remove the parent's reference to the child
			parent.childrenRemove(this);
		}
		// then remove the child's reference to the parent
		this.setParent(null);

	}

	public final void changeParent(final JMudObject newParent) {

		// remove ties to old parent
		this.orphan();

		// establish newParent's reference to this
		if (newParent != null) {
			newParent.childrenAdd(this);
		}
	}

	/**
	 * Directly sets this object's parent JMudObject object reference. *
	 *
	 * @param newParent
	 *            the new parent of this JMudObject
	 */

	private void setParent(final JMudObject newParent) {
		this.parent = newParent;
	}

	public final JMudObject getParent() {
		return this.parent;
	}

	/*                         */
	/*                         */
	/* Information Tools */
	/*                         */
	/*                         */

	@Override
	public final String toString() {
		// QQQ DL: Why StringBuilder over string?
        // AAA CM: Faster: Strings are immutable, adding to one creates another one;
        // the String work might be optimized out by the compiler, but still ...
        StringBuilder out = new StringBuilder(this.toStringShort());

		if (this.parent != null) {
			out.append("\t hasParent:TRUE");
		} else {
			out.append("\t hasParent:FALSE");
		}

		out.append("\t childrenCount:").append(this.childrenSize()).append("\t attrCount:").append(
				this.getAttr().size()).append("\t behaviorCount:").append(this.behaviors.size());

		return out.toString();
	}

	public final String toStringShort() {
		return "JMudObject: " + this.name + "(" + this.uuid.toString() + ")";
	}

	public void sendToConsole(String text) {
		System.out.println("\n" + this.name + "'s console: " + text);
	}

	public void saveToDB() {
		// TODO stubbed the JMudObject db save here.
	}

	public void deleteFromDB() {
		// TODO stubbed the JMudObject db delete here.
	}

}
