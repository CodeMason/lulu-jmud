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
	 * Name just for the sake of Human readability. Not required to be
	 * implemented anywhere.
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

	public JMudObject(UUID uuid, String name, JMudObject parent) {
		super();
		this.parent = parent;
		this.name = name;
		this.uuid = uuid;
	}

	public JMudObject(UUID uuid, String name) {
		this(uuid, name, null);
	}

	public JMudObject(UUID uuid, JMudObject parent) {
		this(uuid, "", parent);
	}

	public JMudObject(UUID uuid) {
		this(uuid, "", null);
	}

	public JMudObject(String name) {
		this(UUID.randomUUID(), name, null);
	}

	public JMudObject(JMudObject parent) {
		this(UUID.randomUUID(), "", parent);
	}

	public JMudObject() {
		this(UUID.randomUUID(), "", null);
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

	public JMudObject getChild(UUID uuid) {
		return children.get(uuid);
	}

	public Map<UUID, JMudObject> getAllChildren() {
		return this.children;
	}

	public JMudObject addChild(JMudObject jmo) {
		jmo.setParent(this);
		return children.put(jmo.getUUID(), jmo);
	}

	public JMudObject remChild(UUID uuid) {
		JMudObject jmo = this.children.remove(uuid);
		if (jmo != null) {
			jmo.setParent(null);
		}
		return jmo;
	}

	public JMudObject remChild(JMudObject jmo) {
		this.remChild(jmo.getUUID());
		return jmo;
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

		Map<UUID, JMudObject> map = null;

		// the ONLY way you should ever have Zero siblings is if you are ROOT
		if (this.parent != null) {
			map = this.parent.getAllChildren();
			map.remove(this.getUUID());
		}
		return map;
	}

	public void orphan() {
		JMudObject parent = this.getParent();

		if (parent != null) {
			// first, remove the parent's reference to the child
			parent.remChild(this);
		}
		// then remove the child's reference to the parent
		this.setParent(null);

	}

	public void changeParent(JMudObject newParent) {

		// remove ties to old parent
		this.orphan();

		// establish newParent's reference to this
		if (newParent != null) {
			newParent.addChild(this);
		} 
	}

	/*
	 * Getter/Setters
	 */

	public JMudObject getParent() {
		return this.parent;
	}

	/**
	 * Directly sets this object's parent JMudObject object reference. WARNING:
	 * the JMudObject tree is double linked. Setting this object's parent
	 * without removing this object from the parent's Children list will violate
	 * the rules of a tree and create a Graph.... bad things will happen if this
	 * is done!
	 * 
	 * @param jmo
	 */
	private void setParent(JMudObject newParent) {
		this.parent = newParent;
	}
	public UUID getUUID() {
		return this.uuid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String n) {
		this.name = n;
	}

	@Override
	public String toString() {
		return this.name + " \t(" + this.uuid.toString() + ")";
	}
}
