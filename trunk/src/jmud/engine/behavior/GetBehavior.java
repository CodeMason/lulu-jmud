package jmud.engine.behavior;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 * Simple representation of a Behavior. This is a Singleton because I can't see
 * any need for having multiple instances, although I did make the behave method
 * synchronized. Any object interested in handling the returned SuccessEvent
 * would register a Behavior for it (e.g. SendSuccessMessageToPlayer); any
 * object without a registered Behavior would just ignore it.
 */
public class GetBehavior extends Behavior {

   public GetBehavior() {
      super();
      // Register a Behavior Object of this type to respond to a
      // EventType.GetEvent
      this.eventTypesHandled.add(JMudEventType.GetEvent);
   }

   /**
    * perform this Behavior's behavior and return the resultant event.
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

   @Override
   public final Behavior clone() {
      synchronized (System.out) {
         System.out.println("GetBehavior.clone()");
      }

      return new GetBehavior();
   }

}
