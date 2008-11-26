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

	public Map<Class<? extends JMudEvent>, List<Behavior>> getBehavior() {
        return behaviors;
	}

    /**
     * Look up the behaviors associated with the Class of the JMudEvent,
     * run all of the Behaviors and then for each Behavior run all the
     * resulting events on the appropriate objects.
     *
     * Allows event chaining: e.g. Player steps on tack, tack hurts player
     *
     * BE CAREFUL OF RECURSIVE EVENTS: maybe we should put a max depth or TTL limit on here?
     *
     * Might always store the triggering event in the resulting event like Exceptions. Would facilitate
     * in debugging event chains.
     *
     * @param event the EventObject to fire
     */
    public void fireEvent(JMudEvent event){
        synchronized(behaviors){
            List<? extends JMudEvent> resultingEvents;
            for(Behavior behavior : behaviors.get(event.getClass())){
                resultingEvents = behavior.behave(event);
                if(resultingEvents != null){
                    for(JMudEvent resultingEvent : behavior.behave(event)){
                        resultingEvent.getSource().fireEvent(resultingEvent);
                    }
                }
            }
        }
    }

    public void registerEventBehaviors(Class<? extends JMudEvent> clazz, List<Behavior> behaviors){
        this.behaviors.get(clazz).addAll(behaviors);
    }
}
