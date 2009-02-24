package jmud.engine.object;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JMudObjectRelationMap {
//	/**
//	 * A mapping of sibling JMudObjects and their relation
//	 */
//	private Map<String, JMudObject> siblingConnectionMap = Collections
//			.synchronizedMap(new HashMap<String, JMudObject>());
//
	/**
	 * A set of child JMudObjects
	 */
	private Set<JMudObject> childObjects = Collections.synchronizedSet(new HashSet<JMudObject>());

	/**
	 * Reference to a Parent JMudObject, if one exists
	 */
	private JMudObject parentObject;

	/*
	 * Sibling Map routines
	 */

//	public Set<String> getAllSiblingRelations() {
//		return this.siblingConnectionMap.keySet();
//	}
//
//	public JMudObject getSiblingByRelation(String relation) {
//		return this.siblingConnectionMap.get(relation);
//	}
//
//	public Set<JMudObject> getAllSiblings() {
//		return (Set<JMudObject>) this.siblingConnectionMap.values();
//	}
//
//	public void addSibling(String relation, JMudObject sibling) {
//		this.siblingConnectionMap.put(relation, sibling);
//	}
//
//	public JMudObject remSiblingByRelation(String relation) {
//		return this.siblingConnectionMap.remove(relation);
//	}

	/*
	 * Parent JMudObject Getter/Setter
	 */
	public JMudObject getParentObject() {
		return this.parentObject;
	}

	public void setParentObject(JMudObject parent) {
		this.parentObject = parent;
	}
	
	public boolean hasParentObject() {
		return (this.parentObject != null);
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

	public boolean remChild(JMudObject jmo) {
		return this.childObjects.remove(jmo);
	}
	public Set<JMudObject> getAllChildren() {
		return this.childObjects;
	}
	
	
	
	/*
	 * Print the Tree
	 */
	
	
	
}
