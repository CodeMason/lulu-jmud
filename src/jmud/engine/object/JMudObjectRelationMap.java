package jmud.engine.object;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JMudObjectRelationMap {
	/**
	 * A Map of JMudObject.DisplayedName to JMudObjects
	 */
	private Map<String, JMudObject> childObjects = Collections.synchronizedMap(new HashMap<String, JMudObject>());

	/**
	 * Reference to a Parent JMudObject, if one exists
	 */
	private JMudObject parentObject;


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
	public void addChild(JMudObject jmo) {
		this.childObjects.put(jmo.getDisplayedName(), jmo);
	}

	public boolean hasChild(JMudObject jmo) {
		return this.childObjects.containsValue(jmo);
	}

	public boolean hasChild(String name) {
		return this.childObjects.containsKey(name);
	}

	public JMudObject remChild(String name) {
		return this.childObjects.remove(name);
	}
	
	public JMudObject remChild(JMudObject jmo) {
		return this.remChild(jmo.getDisplayedName());
	}
	
	public Collection<JMudObject> getAllChildren() {
		return this.childObjects.values();
	}
	public Set<String> getAllChildrenNames() {
		return this.childObjects.keySet();
	}
	
	
	
	/*
	 * Print the Tree
	 */
	
	
	
}
