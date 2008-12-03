package jmud.test;

import jmud.engine.behavior.AttackBehavior;
import jmud.engine.behavior.AttackSourceTriggerEventBehavior;
import jmud.engine.behavior.GetBehavior;
import jmud.engine.event.*;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;

/**
 * Test that an Orc (Orc0) can see when pcSteve
 * *tries* to grab a bag and then attack him.
 */
public class AttackSourceTriggerEventBehaviorTest01{

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

      // Establish behaviors
      bag.addEventBehavior(new GetBehavior(bag));

      // give Orc0 a behavior so that if bag is the target of a GetEvent, AttackSourceTriggerEventBehavior will run
      orc0.addEventBehavior(JMudEventType.GetEvent, new AttackSourceTriggerEventBehavior(orc0), bag);
      orc0.addEventBehavior(new AttackBehavior(orc0));

      // set up event subscriptions

      // set up orc0 to be notified if bag is the target of GetEvent
      JMudEventRegistrar.getInstance().registerSubscription(new JMudEventSubscription(JMudEventType.GetEvent, bag, orc0, JMudEventParticipantRole.TARGET));


      // Printout the tree.
      System.out.println("\n\nOriginal Tree");
      CommonTestMethods.printTreeRecursor(root);
      System.out.println("\n\n");

      // make a new event
      JMudEvent ge = new JMudEvent_ForChaining(JMudEventType.GetEvent, pcSteve, bag);
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