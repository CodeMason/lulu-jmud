package jmud.engine.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.definitions.AbstractJob;

/**
 * Following the Command pattern, a Behavior is a discrete portion of logic that
 * can be associated with a JMudObject and called in response to an Event. Each
 * Behavior will return any resulting Events which will facilitate
 * Event-chaining (think Rube Goldberg). Behaviors should check that they are
 * handling the right event. Each Behavior might list a top level Event class
 * that it handles. ToDo: How will a Behavior get what objects it needs to work
 * on? Should we always pass in the source event and target object?
 */
public abstract class Behavior extends AbstractJob {

   protected static List<JMudEventType> eventTypesHandled = Collections
         .synchronizedList(new ArrayList<JMudEventType>());
   protected JMudEvent event = null;
   private UUID BehaviorID = UUID.randomUUID();
   
   public Behavior() {
   }

   /**
    * perform this Behavior's behavior and return the resultant events, if any.
    * @param event
    *           the event the behavior is responding too
    * @return the resulting EventObject
    */
   protected abstract boolean behave();

   @Override
   public abstract Behavior clone();

   @Override
   public final boolean doJob() {
      return this.behave();
   }

   public final JMudEvent getEvent() {
      return event;
   }

   public final List<JMudEventType> getEventTypesHandled() {
      return eventTypesHandled;
   }

   public final void setEvent(final JMudEvent event) {
      this.event = event;
   }

public UUID getBehaviorID() {
	return BehaviorID;
}

}
