package jmud.test.event;

import jmud.engine.behavior.AttackBehavior;
import jmud.engine.behavior.AttackedBehavior;
import jmud.engine.behavior.GetBehavior;
import jmud.engine.behavior.GotBehavior;
import jmud.engine.behavior.TriggerBehavior;
import jmud.engine.event.*;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;
import jmud.test.CommonTestMethods;

/**
 * 
 * Test auto attack:
 * Assign a trigger to orc0 that monitors bag and responds to any GetEvent
 * with an AttackEvent
 * 
 * Test auto Defend:
 * Assign a trigger to orc1 that monitors pcSteve and responds to any AttackedEvent
 * with an AttackEvent
 * 
 * 
 * 
 */
public class TriggerBehaviorTest01{

   /**
    * @param args command line arguments
    */
   public static void main(final String[] args) {

      // Initialize the JobManager with 3 workers
      JobManager.getInstance().init(1);

      // Initialize the EventRegistrar
      JMudEventRegistrar.getInstance().init();

      // Build a simple JMudObjectTree
      JMudObject root = CommonTestMethods.buildTestJMudObjectTree();

      //get handles on some of the objects
      JMudObject orc0 = root.childrenGet("room").childrenGet("orc0");
      JMudObject orc1 = root.childrenGet("room").childrenGet("orc1");
      JMudObject bag = root.childrenGet("room").childrenGet("bag");
      JMudObject pcSteve = root.childrenGet("room").childrenGet("pcSteve");
 
      // Establish behaviors
      orc0.addEventBehavior(new GetBehavior(orc0));
      orc1.addEventBehavior(new GetBehavior(orc1));
      bag.addEventBehavior(new GetBehavior(bag));
      pcSteve.addEventBehavior(new GetBehavior(pcSteve));
      
      orc0.addEventBehavior(new GotBehavior(orc0));
      orc1.addEventBehavior(new GotBehavior(orc1));
      bag.addEventBehavior(new GotBehavior(bag));
      pcSteve.addEventBehavior(new GotBehavior(pcSteve));
      
      orc0.addEventBehavior(new AttackBehavior(orc0));
      orc1.addEventBehavior(new AttackBehavior(orc1));
      bag.addEventBehavior(new AttackBehavior(bag));
      pcSteve.addEventBehavior(new AttackBehavior(pcSteve));
      
      orc0.addEventBehavior(new AttackedBehavior(orc0));
      orc1.addEventBehavior(new AttackedBehavior(orc1));
      bag.addEventBehavior(new AttackedBehavior(bag));
      pcSteve.addEventBehavior(new AttackedBehavior(pcSteve));


      //Setup orc0's trigger
      TriggerBehavior tb0 = new TriggerBehavior(
    		  orc0, JMudEventType.GetEvent,
    		  JMudEventParticipantRole.TARGET, bag,
    		  JMudEventParticipantRole.SOURCE, JMudEventType.Attack
    		  );
      orc0.addEventBehavior(tb0);

      //Setup orc1's trigger
      TriggerBehavior tb1 = new TriggerBehavior(
    		  orc1, JMudEventType.Attacked,
    		  JMudEventParticipantRole.SOURCE, pcSteve,
    		  JMudEventParticipantRole.TARGET, JMudEventType.Attack
    		  );
      orc1.addEventBehavior(tb1);
      
       
      // Printout the tree.
      System.out.println("\n\nOriginal Tree");
      CommonTestMethods.printTreeRecursor(root);
      System.out.println("\n\n");

      // make the JMudEvent that will cause pcSteve to get the bag
      JMudEvent ge = new JMudEvent(JMudEventType.GetEvent, pcSteve, bag);
      System.out.println("GetEvent eventID is: " + ge.getID());

      // Submit the event
      ge.submitSelf();


      // another sleep to ensure all events are processed
      CommonTestMethods.pause(1000);
      
      // Printout the tree.
      System.out.println("\n\nModified Tree");
      CommonTestMethods.printTreeRecursor(root);
      System.out.println("\n\n");

      JobManager.getInstance().stopAllWorkers();
   }

}