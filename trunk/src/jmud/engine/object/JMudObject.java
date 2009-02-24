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

import jmud.engine.attribute.AttributeMap;
import jmud.engine.behavior.BehaviorMap;

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
	 * Textual name that is displayed in the MUD 
	 * TODO: DHL: Perhaps this should
	 * be an Attribute....
	 */
	private String displayedName = "";

	private BehaviorMap behaviorMap;
	private AttributeMap attributeMap;
	private JMudObjectRelationMap JmoRelationMap;
	
	
	/*
	 * Constructors
	 */
	public JMudObject() {
		this(UUID.randomUUID());
	}

	public JMudObject(UUID uuid) {
		this.uuid = uuid;
		this.attributeMap = new AttributeMap();
	}

	
	/*
	 * Getters n Setters
	 */
	public AttributeMap getAttributeMap(){
		return this.attributeMap;
	}
	public BehaviorMap getBehaviorMap(){
		return this.behaviorMap;
	}
	public JMudObjectRelationMap getJmoRelationMap(){
		return this.JmoRelationMap;
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
	 * IO
	 */
	
	public void sendTextToObject(String text) {
		System.out.println(text);
	}
	
	
}
