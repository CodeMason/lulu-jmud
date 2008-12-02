package jmud.engine.behavior;

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
public class DisplayTextStdErrBehavior extends Behavior {

   /**
    * Default constructor.
    */
   public DisplayTextStdErrBehavior() {
      super();
      this.eventTypesHandled.add(JMudEventType.DisplayTextStdOutEvent);
   }

   /**
    * @see jmud.engine.behavior.Behavior#behave()
    * @return true
    */
   @Override
   public boolean behave() {
      JMudObject source = this.event.getSource();
      JMudObject target = this.event.getTarget();

      // Send off events here!!
      System.err.println("DisplayTextStdErrBehavior(" + this.getBehaviorID()
            + ")" + "\t Source: " + source.getUUID() + "(" + source.getName()
            + ")" + "\t Target: " + target.getUUID() + "(" + target.getName()
            + ")");
      return true;
   }

   /**
    * @see jmud.engine.behavior.Behavior#clone()
    * @return a new <code>GetBeHavior</code>
    */
   @Override
   public Behavior clone() {
      return new GetBehavior();

   }

}
