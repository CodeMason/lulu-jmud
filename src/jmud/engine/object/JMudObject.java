package jmud.engine.object;

import jmud.engine.attribute.AttributeMap;
import jmud.engine.behavior.Behavior;
import jmud.engine.event.JMudEvent;

import java.util.*;


public class JMudObject {

	private JMudObjectMap children = new JMudObjectMap();
	private AttributeMap attr = new AttributeMap();
	//private SynchronizedHashMap<String, Behavior> behaviors = new SynchronizedHashMap<String, Behavior>();

    /**
     * Create a map of events to lists of Behaviors that handle the event
     *
     * With so many possibilities for object behavior, making unique event handlers for each JMudObject will
     * become tedious; by having a list of discrete, atomic behaviors, we can re-use them, e.g. Unlock, Open, Wait, Close, Lock, etc.
     */
    private final Map<Class<? extends JMudEvent>, List<Behavior>> behaviors = Collections.synchronizedMap(new HashMap<Class<? extends JMudEvent>, List<Behavior>>());

    public JMudObjectMap getChildren() {
		return children;
	}

	public AttributeMap getAttr() {
		return attr;
	}

    /**
     * For any event, return the list of applicable behaviors
     * @param event the event to find behaviors for
     * @return the behaviors that match the event
     */
    public List<Behavior> getBehaviors(JMudEvent event){
        return behaviors.get(event.getClass());
    }

    /**
     * Register a list of behaviors with an event class
     * @param clazz Class of JMudEvent to register the behaviors with
     * @param behaviors Behaviors to handle the JMudEvent
     */
    public void registerEventBehaviors(Class<? extends JMudEvent> clazz, List<Behavior> behaviors){
        this.behaviors.get(clazz).addAll(behaviors);
    }
}
