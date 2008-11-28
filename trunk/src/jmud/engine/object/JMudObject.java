package jmud.engine.object;

import jmud.engine.attribute.Attribute;
import jmud.engine.behavior.Behavior;
import jmud.engine.event.JMudEvent;

import java.util.*;

public class JMudObject {

	/*
	 * 
	 * FIELDS
	 */

	/**
	 * UUID for 99.99999% assured ability to differentiate between any/all
	 * JMudObjects.
	 */
	private UUID uuid = null;

	/**
	 * Reference to this object's parent JMudObject object. A null parent
	 * indicates the ROOT JMudObject of the tree.
	 */
	private JMudObject parent = null;

	/**
	 * A HashMap that maps a JMudObject object's UUID to the reference to the
	 * JMudObject.
	 */
	private Map<UUID, JMudObject> children = Collections.synchronizedMap(new HashMap<UUID, JMudObject>());

	/**
	 * A HashMap that maps an Attribute object's string name to the reference to
	 * the Attribute object.
	 */
	private Map<String, Attribute> attr = Collections.synchronizedMap(new HashMap<String, Attribute>());

	/**
	 * Create a map of events to lists of Behaviors that handle the event
	 * 
	 * With so many possibilities for object behavior, making unique event
	 * handlers for each JMudObject will become tedious; by having a list of
	 * discrete, atomic behaviors, we can re-use them, e.g. Unlock, Open, Wait,
	 * Close, Lock, etc.
	 */
	private final Map<Class<? extends JMudEvent>, List<Behavior>> behaviors = Collections
			.synchronizedMap(new HashMap<Class<? extends JMudEvent>, List<Behavior>>());

	/*
	 * 
	 * Constructors
	 */

	public JMudObject(UUID uuid, JMudObject parent) {
		super();
		this.parent = parent;
		this.uuid = uuid;
	}

	public JMudObject(UUID uuid) {
		this(uuid, null);
	}

	/*
	 * 
	 * Methods
	 */

	public Map<String, Attribute> getAttr() {
		return attr;
	}

	/**
	 * For any event, return the list of applicable behaviors
	 * 
	 * @param event
	 *            the event to find behaviors for
	 * @return the behaviors that match the event
	 */
	public List<Behavior> getBehaviors(JMudEvent event) {
		return behaviors.get(event.getClass());
	}

	/**
	 * Register a list of behaviors with an event class
	 * 
	 * @param clazz
	 *            Class of JMudEvent to register the behaviors with
	 * @param behaviors
	 *            Behaviors to handle the JMudEvent
	 */
	public void registerEventBehaviors(Class<? extends JMudEvent> clazz, List<Behavior> behaviors) {
		this.behaviors.get(clazz).addAll(behaviors);
	}

	/*
	 * Children HashMap Delegates
	 */

	public void clear() {
		children.clear();
	}

	public boolean containsKey(UUID uuid) {
		return children.containsKey(uuid);
	}

	public boolean containsValue(JMudObject jmo) {
		return children.containsValue(jmo);
	}

	public Set<UUID> keySet() {
		return children.keySet();
	}

	public JMudObject addChild(JMudObject jmo) {
		return children.put(jmo.getUUID(), jmo);
	}

	public JMudObject getChild(UUID uuid) {
		return children.get(uuid);
	}

	public Map<UUID, JMudObject> getAllChildren() {
		return this.children;
	}

	public JMudObject remChild(UUID uuid) {
		return this.children.remove(uuid);
	}

	public JMudObject remChild(JMudObject jmo) {
		return this.children.remove(jmo.getUUID());
	}

	public int size() {
		return this.children.size();
	}

	public Collection<JMudObject> values() {
		return this.children.values();
	}

	/*
	 * Parent/Child shortcuts
	 */

	public Map<UUID, JMudObject> getSiblings() {
		Map<UUID, JMudObject> map = this.parent.getAllChildren();
		map.remove(this.getUUID());
		return map;
	}

	/*
	 * Parent Getter/Setter
	 */

	public JMudObject getParent() {
		synchronized (this.parent) {
			return this.getParent();
		}
	}

	public void setParent(JMudObject jmo) {
		synchronized (this.parent) {
			this.parent = jmo;
		}
	}

	/*
	 * UUID Getter/Setter
	 */

	public UUID getUUID() {
		return this.uuid;
	}

}
