package jmud.engine.behavior;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 * Simple representation of a <code>Behavior</code>. This is a singleton because
 * I can't see any need for having multiple instances, although I did make the
 * <code>behave</code> method synchronized. Any object interested in handling
 * the returned <code>SuccessEvent</code> would register a <code>Behavior</code>
 * for it (e.g. <code>SendSuccessMessageToPlayer</code>); any object without a
 * registered <code>Behavior</code> would just ignore it.
 * @author david.h.loman
 */
public class GetBehavior extends Behavior {

   /**
    * Default constructor.
    */
   public GetBehavior() {
      super();
      // Register a Behavior Object of this type to respond to a
      // EventType.GetEvent
      this.eventTypesHandled.add(JMudEventType.GetEvent);
   }

   /**
    * @see jmud.engine.behavior.Behavior#behave()
    * @return true
    */
   @Override
   public final boolean behave() {
      JMudObject source = this.event.getSource();
      JMudObject target = this.event.getTarget();

      synchronized (System.out) {
         System.out.println("GetBehavior.behave():  EventType: "
               + this.event.getEventType() + " Source: "
               + this.event.getSource().getName() + " Target: "
               + this.event.getTarget().getName());
      }

      target.orphan();
      source.childrenAdd(target);

      // prep the 'response' JMudEvent
      JMudEvent jme = new JMudEvent(JMudEventType.SuccessEvent, this.event
            .getTarget(), this.event.getSource());
      jme.submitSelf();

      return true;
   }

   /**
    * @see jmud.engine.behavior.Behavior#clone()
    * @return a new <code>GetBeHavior</code>
    */
   @Override
   public final Behavior clone() {
      synchronized (System.out) {
         System.out.println("GetBehavior.clone()");
      }

      return new GetBehavior();
   }

}
