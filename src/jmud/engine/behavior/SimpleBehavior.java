package jmud.engine.behavior;

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
public class SimpleBehavior extends Behavior {

   /**
    * Default constructor.
    */
   public SimpleBehavior() {
      super();
   }

   /**
    * @see jmud.engine.behavior.Behavior#behave()
    * @return true
    */
   @Override
   public final boolean behave() {
      JMudObject source = this.event.getSource();
      JMudObject targets = this.event.getTarget();

      // Send off events here!!

      return true;
   }

   /**
    * @see jmud.engine.behavior.Behavior#clone()
    * @return a new <code>GetBeHavior</code>
    */
   @Override
   public final Behavior clone() {
      return new GetBehavior();

   }

}
