package jmud.engine.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.definitions.AbstractJob;

/**
 * Following the command design pattern [GoF], a <code>Behavior</code> is a
 * discrete portion of logic that can be associated with a
 * <code>JMudObject</code> and called in response to a <code>JMUDEvent</code>.
 * Each <code>Behavior</code> will return any resulting events which will
 * facilitate event-chaining (think Rube Goldberg). <code>Behaviors</code>
 * should check that they are handling the right event. Each
 * <code>Behavior</code> might list a top level event class that it handles.
 * ToDo: How will a <code>Behavior</code> get what objects it needs to work on?
 * Should we always pass in the source event and target object?
 * @author david.h.loman
 */
public abstract class Behavior extends AbstractJob {

   protected static List<JMudEventType> eventTypesHandled = Collections
         .synchronizedList(new ArrayList<JMudEventType>());
   private UUID BehaviorID = UUID.randomUUID();
   protected JMudEvent event = null;

   /**
    * Default constructor.
    */
   public Behavior() {
   }

   /**
    * perform this <code>Behavior's</code> behavior.
    * @return true if behavior completes successfully
    */
   protected abstract boolean behave();

   /**
    * Typical clone implementation.
    * @see java.lang.Object#clone()
    * @return a cloned <code>Behavior</code>
    */
   @Override
   public abstract Behavior clone();

   /**
    * @see jmud.engine.job.definitions.AbstractJob#doJob()
    * @return true if <code>doJob</code>, and thus, <code>behave</code> complete
    *         successfully
    */
   @Override
   public final boolean doJob() {
      return this.behave();
   }

   /**
    * @return the bevaior ID
    */
   public UUID getBehaviorID() {
      return BehaviorID;
   }

   /**
    * @return the <code>JMudEvent</code>
    */
   public final JMudEvent getEvent() {
      return event;
   }

   /**
    * @return the list of event types handled
    */
   public final List<JMudEventType> getEventTypesHandled() {
      return eventTypesHandled;
   }

   /**
    * @param inEvent
    *           the set event
    */
   public final void setEvent(final JMudEvent inEvent) {
      this.event = inEvent;
   }

}
