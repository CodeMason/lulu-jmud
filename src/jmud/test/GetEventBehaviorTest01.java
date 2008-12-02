package jmud.test;

import jmud.engine.behavior.DisplayTextStdErrBehavior;
import jmud.engine.behavior.DisplayTextStdOutBehavior;
import jmud.engine.behavior.GetBehavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventRegistrar;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;

public class GetEventBehaviorTest01 {

   /**
    * @param args
    */
   public static void main(final String[] args) {

      // Initialize the JobManager with 1 worker
      JobManager.getInstance().init(1);
      
      // Initialize the EventRegistrar
      JMudEventRegistrar.getInstance().init();

      // Build a simple JMudObjectTree
      JMudObject root = CommonTestParts.buildTestJMudObjectTree();
      
      //get handles on some of the objects
      JMudObject bag = root.childrenGet("room").childrenGet("bag");
      JMudObject pcSteve = root.childrenGet("room").childrenGet("pcSteve");
      JMudObject orc0 = root.childrenGet("room").childrenGet("orc0");
      JMudObject orc1 = root.childrenGet("room").childrenGet("orc1");

      // Establish behaviors
      bag.addEventBehavior(new GetBehavior());
      orc0.addEventBehavior(new DisplayTextStdOutBehavior());
      orc0.addEventBehavior(new DisplayTextStdErrBehavior());
      orc1.addEventBehavior(new DisplayTextStdOutBehavior());
      orc1.addEventBehavior(new DisplayTextStdErrBehavior());
      
           
      // Printout the tree.
      System.out.println("\n\nOriginal Tree");
      CommonTestParts.printTreeRecursor(root);
      System.out.println("\n\n");
      
      // make a new event
      JMudEvent ge = new JMudEvent(JMudEventType.GetEvent, pcSteve, bag);
      System.out.println("GetEvent eventID is: " + ge.getID());


      // short sleep so we can test 'waking' a JobWorker
      try {
         Thread.sleep(250);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      // Submit the event
      ge.submitSelf();

      // another sleep to ensure all events are processed
      try {
         Thread.sleep(2500);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      // Printout the tree.
      System.out.println("\n\nModified Tree");
      CommonTestParts.printTreeRecursor(root);

   }

}
