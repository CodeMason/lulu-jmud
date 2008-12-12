package jmud.engine.object;

import jmud.engine.attribute.Attribute;
import jmud.engine.behavior.Behavior;
import jmud.engine.behavior.SendToConsoleBehavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;

import java.util.*;

/**
 * Represents any "thing" in the mud
 */
public class JMudObject {
    private static final JMudObject ROOT_PARENT = null;
    private static final List<Class> DEFAULT_BEHAVIOR_CLASSES = Arrays.asList((Class)SendToConsoleBehavior.class);

    private UUID uuid = null;
	private String humanReadableName = "";
	private JMudObject parentObject = ROOT_PARENT;
	private final Map<UUID, JMudObject> childObjects = Collections.synchronizedMap(new HashMap<UUID, JMudObject>());
	private final Map<String, Attribute> attributesByName = Collections.synchronizedMap(new HashMap<String, Attribute>());
	private final Map<JMudEventType, List<Behavior>> eventTypeBehaviors = Collections.synchronizedMap(new EnumMap<JMudEventType, List<Behavior>>(JMudEventType.class));

	/**
	 * Default constructor.
	 */
	public JMudObject() {
		this(UUID.randomUUID(), "", ROOT_PARENT);
	}

	public JMudObject(JMudObject parentObject) {
		this(UUID.randomUUID(), "", parentObject);
	}

	public JMudObject(String humanReadableName) {
		this(UUID.randomUUID(), humanReadableName, null);
	}

	public JMudObject(String humanReadableName, JMudObject parentObject) {
		this(UUID.randomUUID(), humanReadableName, parentObject);
	}

	private JMudObject(UUID uuid, String humanReadableName, final JMudObject parentObject) {
		this.parentObject = parentObject;
		this.humanReadableName = humanReadableName;
		this.uuid = uuid;
        registerDefaultBehaviorsForEventTypesHandled();
    }

    private void registerDefaultBehaviorsForEventTypesHandled(){
        this.registerBehaviorsForEventTypesHandled(getInstancesOfDefaultBehaviorClasses());
    }

    private List<Behavior> getInstancesOfDefaultBehaviorClasses(){
        List<Behavior> defaultBehaviors = new ArrayList<Behavior>();
        Behavior defaultBehavior;

        for(Class behaviorClass : DEFAULT_BEHAVIOR_CLASSES){
            if(Behavior.class.isAssignableFrom(behaviorClass)){
                defaultBehavior = getBehaviorInstance(behaviorClass);
                if(defaultBehavior != null){
                    defaultBehaviors.add(defaultBehavior);
                }
            }
        }

        return defaultBehaviors;
    }

    private Behavior getBehaviorInstance(Class behaviorClass){
        Behavior defaultBehavior;
        try{
            defaultBehavior = (Behavior) behaviorClass.getConstructor(JMudObject.class).newInstance(this);
        }catch(Exception e){
            System.out.println("Could not instantiate instance of default Behavior " + behaviorClass.getName() + " from " + this.toStringShort() + "\n" + e);
            defaultBehavior = null;
        }
        return defaultBehavior;
    }

    private void registerBehaviorsForEventTypesHandled(List<Behavior> behaviorsToRegister){
        for(Behavior behaviorToRegister : behaviorsToRegister){
            registerBehaviorForEventTypesHandled(behaviorToRegister);
        }
    }

    /**
	 * Register a behaviors with an event class.
	 *
	 * @param behavior Behavior to mapped to Behavior.getEventTypesHandled();
	 */
	public void registerBehaviorForEventTypesHandled(Behavior behavior) {
		for (JMudEventType eventType : behavior.getEventTypesHandled()) {
			List<Behavior> eventBehaviors = getBehaviors(eventType);
            eventBehaviors.add(behavior);
			this.eventTypeBehaviors.put(eventType, eventBehaviors);
		}
	}

	/**
	 * For any event, return the list of applicable behaviors
	 *
	 * @param event the event to find behaviors for
	 * @return the behaviors that match the event
	 */
	public List<Behavior> getBehaviors(JMudEvent event) {
		return this.getBehaviors(event.getEventType());
	}

	public List<Behavior> getBehaviors(JMudEventType eventType) {
        List<Behavior> existingEventBehaviors = eventTypeBehaviors.get(eventType);
        return Collections.synchronizedList(existingEventBehaviors != null ? existingEventBehaviors : new ArrayList<Behavior>());
	}

	public JMudObject addChildObject(JMudObject object) {
		object.setParentObject(this);
		return this.childObjects.put(object.getUuid(), object);
	}

	public void clearChildObjects() {
		childObjects.clear();
	}

	public boolean hasChildObject(UUID uuid) {
		return childObjects.containsKey(uuid);
	}

	public boolean hasChildObject(JMudObject object) {
		return childObjects.containsValue(object);
	}

	// TODO CM: we may want to look at optimizing this (maybe name -> uuid hash?)
	public JMudObject getChildObject(String objectName) {
		for (JMudObject childObject : this.childObjects.values()) {
			if (childObject.getHumanReadableName().equals(objectName)) {
				return childObject;
			}
		}
		return null;
	}

	public JMudObject getChild(final UUID uuid) {
		return childObjects.get(uuid);
	}

	public Map<UUID, JMudObject> getChildObjects() {
		return this.childObjects;
	}

	public Set<UUID> getChildObjectKeys() {
		return childObjects.keySet();
	}

	public JMudObject childrenRemove(final JMudObject jmo) {
		this.childrenRemove(jmo.getUuid());
		return jmo;
	}

	public JMudObject childrenRemove(final UUID uuid) {
		JMudObject jmo = this.childObjects.remove(uuid);
		if (jmo != null) {
			jmo.setParentObject(null);
		}
		return jmo;
	}

	public int childrenSize() {
		return this.childObjects.size();
	}

	public Collection<JMudObject> childrenValues() {
		return this.childObjects.values();
	}

	public String getHumanReadableName() {
		return this.humanReadableName;
	}

	public void setHumanReadableName(String objectName) {
		this.humanReadableName = objectName;
	}

	public UUID getUuid() {
		return this.uuid;
	}

	/**
	 * Get a handle on the Attribute Map
	 *
	 * @return
	 */
	public Map<String, Attribute> getAttributesByName() {
		return attributesByName;
	}

	/*                         */
	/*                         */
	/* Hierarchy Tools */
	/*                         */
	/*                         */

	public final Map<UUID, JMudObject> getSiblings() {

		Map<UUID, JMudObject> map = null;

		// the ONLY way you should ever have Zero siblings is if you are ROOT
		// QQQ CM You mean the only way you won't have a parentObject? (I could be the
		// only thing in a room, the only thing in a bag, the only brain cell in
		// Dave's head (KIDDING!), etc.)
		// AAA DHL: Root, by definition will have no siblings nor will it have a
		// parentObject. (ignores the brain cell thing :P)
        // QQQ CM: It's just that below, you're not referring to siblings, you're
        // referring to the parentObject. :)

        if (this.parentObject != null) {
			map = this.parentObject.getChildObjects();
			// filter out the calling object
			map.remove(this.getUuid());
		}
		return map;
	}

	public final void orphan() {
		// System.err.println("\nOrphaning " + this.toStringShort() + "\n");

		JMudObject parent = this.getParentObject();

		if (parent != null) {
			// first, remove the parentObject's reference to the child
			parent.childrenRemove(this);
		}
		// then remove the child's reference to the parentObject
		this.setParentObject(null);

	}

	public final void changeParent(final JMudObject newParent) {

		// remove ties to old parent
		this.orphan();

		// establish newParent's reference to this
		if (newParent != null) {
			newParent.addChildObject(this);
		}
	}

	/**
	 * Directly sets this object's parent JMudObject object reference. *
	 *
	 * @param newParent
	 *            the new parent of this JMudObject
	 */

	private void setParentObject(final JMudObject newParent) {
		this.parentObject = newParent;
	}

	public final JMudObject getParentObject() {
		return this.parentObject;
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

		if (this.parentObject != null) {
			out.append("\t hasParent:TRUE");
		} else {
			out.append("\t hasParent:FALSE");
		}

		out.append("\t childrenCount:").append(this.childrenSize()).append("\t attrCount:").append(
				this.getAttributesByName().size()).append("\t behaviorCount:").append(this.eventTypeBehaviors.size());

		return out.toString();
	}

	public final String toStringShort() {
		return "JMudObject: " + this.humanReadableName + "(" + this.uuid.toString() + ")";
	}

	public void sendToConsole(String text) {
		System.out.println("\n" + this.humanReadableName + "'s console: " + text);
	}

	public void saveToDB() {
		// TODO stubbed the JMudObject db save here.
	}

	public void deleteFromDB() {
		// TODO stubbed the JMudObject db delete here.
	}

}
