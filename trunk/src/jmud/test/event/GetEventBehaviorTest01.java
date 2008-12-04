package jmud.test.event;

import jmud.engine.behavior.GetBehavior;
import jmud.engine.behavior.GotBehavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventRegistrar;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;
import jmud.test.CommonTestMethods;

public class GetEventBehaviorTest01 {

   /**
    * @param args command line arguments
    */
   public static void main(final String[] args) {

      // Initialize the JobManager with 3 workers
      JobManager.getInstance().init(3);

      // Initialize the EventRegistrar
      JMudEventRegistrar.getInstance().init();

      // Build a simple JMudObjectTree
      JMudObject root = CommonTestMethods.buildTestJMudObjectTree();

      //get handles on some of the objects
      JMudObject bag = root.childrenGet("room").childrenGet("bag");
      JMudObject pcSteve = root.childrenGet("room").childrenGet("pcSteve");
      JMudObject orc0 = root.childrenGet("room").childrenGet("orc0");
      JMudObject orc1 = root.childrenGet("room").childrenGet("orc1");
      JMudObject door = root.childrenGet("room").childrenGet("door");

      // Establish behaviors
      bag.addEventBehavior(new GetBehavior(bag));

      bag.addEventBehavior(new GotBehavior(bag));
      pcSteve.addEventBehavior(new GotBehavior(pcSteve));
      orc0.addEventBehavior(new GotBehavior(orc0));
      orc1.addEventBehavior(new GotBehavior(orc1));
      door.addEventBehavior(new GotBehavior(door));


      // Printout the tree.
      System.out.println("\n\nOriginal Tree");
      CommonTestMethods.printTreeRecursor(root);
      System.out.println("\n\n");

      // make a new event
      JMudEvent ge = new JMudEvent(JMudEventType.GetEvent, pcSteve, bag);
      System.out.println("GetEvent eventID is: " + ge.getID());


      // short sleep so we can test 'waking' a JobWorker
      CommonTestMethods.pause(250);

      // Submit the event
      ge.submitSelf();

      // another sleep to ensure all events are processed
      CommonTestMethods.pause(1000);

      // Printout the tree.
      System.out.println("\n\nModified Tree");
      CommonTestMethods.printTreeRecursor(root);
      System.out.println("\n\n");



      // another sleep to ensure all events are processed
      CommonTestMethods.pause(2500);

      JobManager.getInstance().stopAllWorkers();
   }

}
