package jmud.engine.object;

import jmud.engine.attribute.Attribute;
import jmud.engine.behavior.Behavior;
import jmud.engine.event.EventType;
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
	private final Map<EventType, List<Behavior>> behaviors = Collections
			.synchronizedMap(new HashMap<EventType, List<Behavior>>());

	/*
	 * 
	 * Constructors
	 */

	private JMudObject(UUID uuid, String name, JMudObject parent) {
		super();
		this.parent = parent;
		this.name = name;
		this.uuid = uuid;
	}

	public JMudObject(String name, JMudObject parent) {
		this(UUID.randomUUID(), name, parent);
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

	/**
	 * For any event, return the list of applicable behaviors
	 * 
	 * @param event
	 *            the event to find behaviors for
	 * @return the behaviors that match the event
	 */
	public List<Behavior> getBehaviors(JMudEvent event) {
		return this.getBehaviors(event.getEventType());
	}

	public List<Behavior> getBehaviors(EventType et) {
		return behaviors.get(et);
	}

	/**
	 * Register a behaviors with an event class
	 * 
	 * @param b Behavior to mapped to Behavior.getEventTypesHandled();
	 */
	public void addEventBehavior(Behavior b) {
		List<EventType> ets = b.getEventTypesHandled();
		
		for (EventType e : ets) {
			
			List<Behavior> behs = this.behaviors.get(e);
			
			if (behs == null) {
				//There was no mapping for EventType e, so make a newone
				behs = new ArrayList<Behavior>();
				behs.add(b);
				this.behaviors.put(e, behs);
			} else {
				//There was a mapping for EventType e
				behs.add(b);
			}
		}
	}


	
	
	
	/*
	 * Attribute HashMap Delegates
	 */

	public void attributeClear() {
		attr.clear();
	}

	public boolean attributeContainsKey(String key) {
		return attr.containsKey(key);
	}

	public boolean attributeContainsValue(Attribute value) {
		return attr.containsValue(value);
	}

	public Attribute attributeGet(String key) {
		return attr.get(key);
	}

	public Set<String> attributeKeySet() {
		return attr.keySet();
	}

	public Attribute attributePut(String key, Attribute value) {
		return attr.put(key, value);
	}

	public Attribute attributeRemove(String key) {
		return attr.remove(key);
	}

	public int attributeAize() {
		return attr.size();
	}

	public Collection<Attribute> attributeValues() {
		return attr.values();
	}

	/*
	 * Children HashMap Delegates
	 */

	public void childrenClear() {
		children.clear();
	}

	public boolean childrenContainsKey(UUID uuid) {
		return children.containsKey(uuid);
	}

	public boolean childrenContainsValue(JMudObject jmo) {
		return children.containsValue(jmo);
	}

	public Set<UUID> childrenKeySet() {
		return children.keySet();
	}

	public JMudObject childrenGet(UUID uuid) {
		return children.get(uuid);
	}

	public JMudObject childrenGet(String name) {
		for (JMudObject jmo : this.children.values()) {
			if (jmo.getName().equals(name)) {
				return jmo;
			}
		}
		return null;
	}

	public Map<UUID, JMudObject> childrenGetAll() {
		return this.children;
	}

	public JMudObject childrenAdd(JMudObject jmo) {
		jmo.setParent(this);
		return children.put(jmo.getUUID(), jmo);
	}

	public JMudObject childrenRemove(UUID uuid) {
		JMudObject jmo = this.children.remove(uuid);
		if (jmo != null) {
			jmo.setParent(null);
		}
		return jmo;
	}

	public JMudObject childrenRemove(JMudObject jmo) {
		this.childrenRemove(jmo.getUUID());
		return jmo;
	}

	public int childrenSize() {
		return this.children.size();
	}

	public Collection<JMudObject> childrenValues() {
		return this.children.values();
	}

	/*
	 * Parent/Child shortcuts
	 */

	public Map<UUID, JMudObject> getSiblings() {

		Map<UUID, JMudObject> map = null;

		// the ONLY way you should ever have Zero siblings is if you are ROOT
		if (this.parent != null) {
			map = this.parent.childrenGetAll();
			map.remove(this.getUUID());
		}
		return map;
	}

	public void orphan() {
		JMudObject parent = this.getParent();

		if (parent != null) {
			// first, remove the parent's reference to the child
			parent.childrenRemove(this);
		}
		// then remove the child's reference to the parent
		this.setParent(null);

	}

	public void changeParent(JMudObject newParent) {

		// remove ties to old parent
		this.orphan();

		// establish newParent's reference to this
		if (newParent != null) {
			newParent.childrenAdd(this);
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
