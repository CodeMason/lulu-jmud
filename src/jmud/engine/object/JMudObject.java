package jmud.engine.object;

import jmud.engine.attribute.AttributeMap;
import jmud.engine.behavior.BehaviorMap;

public class JMudObject {

	private JMudObjectMap children = new JMudObjectMap();
	private AttributeMap attr = new AttributeMap();
	private BehaviorMap behavior = new BehaviorMap();

	public JMudObjectMap getChildren() {
		return children;
	}

	public AttributeMap getAttr() {
		return attr;
	}

	public BehaviorMap getBehavior() {
		return behavior;
	}

}
