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
    private static final int NUM_JOB_WORKERS = 1;
    private static JMudObject orc0;
    private static JMudObject orc1;
    private static JMudObject bag;
    private static JMudObject pcSteve;

    /**
    * @param args command line arguments
    */
   public static void main(final String[] args) {

      JobManager.getLazyLoadedInstance().init(NUM_JOB_WORKERS);

      JMudEventRegistrar.getLazyLoadedInstance();

      JMudObject root = CommonTestMethods.buildSimpleJMudObjectTree();

      createTestJMudObjects(root);
      addNewJMudObjectTestBehaviors();

      //Setup orc0's trigger
      TriggerBehavior tb0 = new TriggerBehavior(orc0, JMudEventType.Get, JMudEventParticipantRole.TARGET, bag, JMudEventParticipantRole.SOURCE, JMudEventType.Attack);
      orc0.addEventBehavior(tb0);

      //Setup orc1's trigger
      TriggerBehavior tb1 = new TriggerBehavior(orc1, JMudEventType.Attacked, JMudEventParticipantRole.SOURCE, pcSteve, JMudEventParticipantRole.TARGET, JMudEventType.Attack);
      orc1.addEventBehavior(tb1);

      // Printout the tree.
      System.out.println("\n\nOriginal Tree");
      CommonTestMethods.printJMudObjectTree(root);
      System.out.println("\n\n");

      // make the JMudEvent that will cause pcSteve to get the bag
      JMudEvent ge = new JMudEvent(JMudEventType.Get, pcSteve, bag);
      System.out.println("GetEvent eventID is: " + ge.getID());

      // Submit the event
      ge.submitSelf();


      // another sleep to ensure all events are processed
      CommonTestMethods.pause(1000);

      // Printout the tree.
      System.out.println("\n\nModified Tree");
      CommonTestMethods.printJMudObjectTree(root);
      System.out.println("\n\n");

      JobManager.getLazyLoadedInstance().stopAllWorkers();
   }

    private static void addNewJMudObjectTestBehaviors(){
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
    }

    private static void createTestJMudObjects(JMudObject root){
        orc0 = root.childrenGet("room").childrenGet("orc0");
        orc1 = root.childrenGet("room").childrenGet("orc1");
        bag = root.childrenGet("room").childrenGet("bag");
        pcSteve = root.childrenGet("room").childrenGet("pcSteve");
    }

}