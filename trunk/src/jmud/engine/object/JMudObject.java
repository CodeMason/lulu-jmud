/**
 * This file is part of Lulu's JMud.
 *
 *  Lulu's JMud is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Lulu's JMud is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Lulu's JMud.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmud.engine.object;

import jmud.engine.attribute.Attribute;
import jmud.engine.behavior.Behavior;
import jmud.engine.behavior.BehaviorFactory;
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
		this("", ROOT_PARENT);
	}

	public JMudObject(JMudObject parentObject) {
		this("", parentObject);
	}

	public JMudObject(String humanReadableName) {
		this(humanReadableName, ROOT_PARENT);
	}

	private JMudObject(String humanReadableName, final JMudObject parentObject) {
		this.parentObject = parentObject;
		this.humanReadableName = humanReadableName;
		this.uuid = UUID.randomUUID();
        registerDefaultBehaviorsForEventTypesHandled();
    }

    private void registerDefaultBehaviorsForEventTypesHandled(){
        this.registerBehaviorsForEventTypesHandled(BehaviorFactory.createBehaviors(DEFAULT_BEHAVIOR_CLASSES, this));
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

    public int childrenSize(){
        return this.childObjects.size();
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
	 * Get the Attribute Map
	 *
	 * @return
	 */
	public Map<String, Attribute> getAttributesByName() {
		return attributesByName;
	}

	public final Map<UUID, JMudObject> getSiblings() {

		Map<UUID, JMudObject> map = null;

        if (this.parentObject != null) {
			map = this.parentObject.getChildObjects();
            removeCurrentObject(map);
        }
		return map;
	}

    private void removeCurrentObject(Map<UUID, JMudObject> map){
        map.remove(this.getUuid());
    }

    public final void orphan() {
        removeFromParent();
		this.setParentObject(null);

	}

    private void removeFromParent(){
        JMudObject parent = this.getParentObject();

        if (parent != null) {
            parent.childrenRemove(this);
        }
    }

    public final void changeParent(JMudObject newParent) {
		if (newParent != null) {
            this.orphan();
			newParent.addChildObject(this);
            this.setParentObject(newParent);
        }
	}

	/**
	 * Directly sets this object's parent JMudObject object reference.
	 *
	 * @param newParent
	 *            the new parent of this JMudObject
	 */
	private void setParentObject(JMudObject newParent) {
		this.parentObject = newParent;
	}

	public final JMudObject getParentObject() {
		return this.parentObject;
	}

	@Override
	public final String toString() {
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
}
