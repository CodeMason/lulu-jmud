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
import jmud.engine.behavior.AbstractBehavior;
import jmud.engine.event.JMudEventType;

import java.util.*;

/**
 * Represents any "thing" in the mud
 */
public class JMudObject {

	/**
	 * Universally Unique Identifier
	 */
	private UUID uuid = null;

	/**
	 * Textual name that is displayed in the MUD TODO: DHL: Perhaps this should
	 * be an Attribute....
	 */
	private String displayedName = "";

	/**
	 * A mapping of sibling JMudObjects and their relation
	 */
	private Map<String, JMudObject> siblingConnectionMap = Collections
			.synchronizedMap(new HashMap<String, JMudObject>());

	/**
	 * A set of child JMudObjects
	 */
	private Set<JMudObject> childObjects = Collections.synchronizedSet(new HashSet<JMudObject>());

	/**
	 * Reference to a Parent JMudObject, if one exists
	 */
	private JMudObject parentObject;

	/**
	 * A mapping of events to behaviors
	 */
	private Map<JMudEventType, AbstractBehavior> behaviorMap = Collections
			.synchronizedMap(new HashMap<JMudEventType, AbstractBehavior>());

	/**
	 * A mapping of Attribute names to Attribute references
	 */
	private Map<String, Attribute> attributeMap = Collections.synchronizedMap(new HashMap<String, Attribute>());

	/*
	 * Constructors
	 */
	public JMudObject() {
		this(UUID.randomUUID());
	}

	public JMudObject(UUID uuid) {
		this.uuid = uuid;
	}

	/*
	 * Sibling Map routines
	 */

	public Set<String> getRelations() {
		return this.siblingConnectionMap.keySet();
	}

	public JMudObject getSiblingByRelation(String relation) {
		return this.siblingConnectionMap.get(relation);
	}

	public Set<JMudObject> getAllSibling() {
		return (Set<JMudObject>) this.siblingConnectionMap.values();
	}

	public void addSibling(String relation, JMudObject sibling) {
		this.siblingConnectionMap.put(relation, sibling);
	}

	public JMudObject remSiblingByRelation(String relation) {
		return this.siblingConnectionMap.remove(relation);
	}

	/*
	 * Parent JMudObject Getter/Setter
	 */
	public JMudObject getParentObject() {
		return this.parentObject;
	}

	public void setParentObject(JMudObject parent) {
		this.parentObject = parent;
	}

	/*
	 * Child JMudObject Set routines
	 */
	public boolean addChild(JMudObject jmo) {
		return this.childObjects.add(jmo);
	}

	public boolean hasChild(JMudObject jmo) {
		return this.childObjects.contains(jmo);
	}

	public boolean remove(JMudObject jmo) {
		return this.childObjects.remove(jmo);
	}
	public Set<JMudObject> getAllChildren() {
		return this.childObjects;
	}

	/*
	 * UUID Getter
	 */
	public UUID getUUID() {
		return this.uuid;
	}

	/*
	 * Displayed Name Getter/Setter
	 */
	public String getDisplayedName() {
		return displayedName;
	}

	public void setDisplayedName(String displayedName) {
		this.displayedName = displayedName;
	}

	/*
	 * Attribute Map Routines
	 */
	public boolean containsAttribute(String attrName) {
		return this.attributeMap.containsKey(attrName);
	}

	public boolean containsAttribute(Attribute a) {
		return this.attributeMap.containsValue(a);
	}

	public Attribute getAttribute(String attrName) {
		return this.attributeMap.get(attrName);
	}

	public Set<String> getAttributeNames() {
		return this.attributeMap.keySet();
	}

	public Set<Attribute> getAttributes() {
		return (Set<Attribute>) this.attributeMap.values();
	}

	public Attribute addAttribute(String name, Attribute a) {
		return this.attributeMap.put(name, a);
	}

	public Attribute remAttribute(String name) {
		return this.attributeMap.remove(name);
	}

	/*
	 * Behavior Map Routines
	 */

	public boolean containsBehavior(AbstractBehavior b) {
		return behaviorMap.containsValue(b);
	}

	public AbstractBehavior getBehavior(JMudEventType jmet) {
		return behaviorMap.get(jmet);
	}

	public Set<JMudEventType> getEventTypes() {
		return behaviorMap.keySet();
	}

	public AbstractBehavior addBehavior(JMudEventType jmet, AbstractBehavior b) {
		return behaviorMap.put(jmet, b);
	}

	public AbstractBehavior removeBehavior(JMudEventType jmet) {
		return behaviorMap.remove(jmet);
	}

	public Set<AbstractBehavior> getBehaviors() {
		return (Set<AbstractBehavior>) behaviorMap.values();
	}

	
	/*
	 * IO
	 */
	
	public void sendTextToObject(String text) {
		System.out.println(text);
	}
	
	
}
