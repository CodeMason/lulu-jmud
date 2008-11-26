package jmud.engine.behavior;

import jmud.engine.event.JMudEvent;

import java.util.List;

/**
 * Following the Command pattern, a Behavior is a discrete
 * portion of logic that can be associated with a JMudObject
 * and called in response to an Event.
 *
 * Each Behavior will return any resulting Events which will
 * facilitate Event-chaining (think Rube Goldberg).
 *
 * Behaviors should check that they are handling the right event.
 * Each Behavior might list a top level Event class that it handles.
 */
public abstract class Behavior {

    /**
     * perform this Behavior's behavior and return the resultant
     * events, if any.
     *
     * @param event the event the bahavior is responding too
     * @return the resulting EventObject
     */
    public abstract List<? extends JMudEvent> behave(JMudEvent event);

    public abstract List<Class> getValidEvents();
}
