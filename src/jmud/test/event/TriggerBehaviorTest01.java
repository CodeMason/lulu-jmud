package jmud.test.event;

import jmud.engine.behavior.AttackBehavior;
import jmud.engine.behavior.AttackedBehavior;
import jmud.engine.behavior.GetBehavior;
import jmud.engine.behavior.GotBehavior;
import jmud.engine.behavior.TriggerBehavior;
import jmud.engine.event.*;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;
import jmud.test.TestUtil;

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

      JMudObject root = TestUtil.buildSimpleJMudObjectTree();

      createTestJMudObjects(root);
      addNewJMudObjectTestBehaviors();

      //Setup orc0's trigger
      TriggerBehavior tb0 = new TriggerBehavior(orc0, JMudEventType.Get, JMudEventParticipantRole.TARGET, bag, JMudEventParticipantRole.SOURCE, JMudEventType.Attack);
      orc0.registerBehaviorForEventTypesHandled(tb0);

      //Setup orc1's trigger
      TriggerBehavior tb1 = new TriggerBehavior(orc1, JMudEventType.Attacked, JMudEventParticipantRole.SOURCE, pcSteve, JMudEventParticipantRole.TARGET, JMudEventType.Attack);
      orc1.registerBehaviorForEventTypesHandled(tb1);

      // Printout the tree.
      System.out.println("\n\nOriginal Tree");
      TestUtil.printJMudObjectTree(root);
      System.out.println("\n\n");

      // make the JMudEvent that will cause pcSteve to get the bag
      JMudEvent ge = new JMudEvent(JMudEventType.Get, pcSteve, bag);
      System.out.println("GetEvent eventID is: " + ge.getID());

      // Submit the event
      ge.submitSelf();


      // another sleep to ensure all events are processed
      TestUtil.pause(1000);

      // Printout the tree.
      System.out.println("\n\nModified Tree");
      TestUtil.printJMudObjectTree(root);
      System.out.println("\n\n");

      JobManager.getLazyLoadedInstance().stopAllWorkers();
   }

    private static void addNewJMudObjectTestBehaviors(){
        orc0.registerBehaviorForEventTypesHandled(new GetBehavior(orc0));
        orc1.registerBehaviorForEventTypesHandled(new GetBehavior(orc1));
        bag.registerBehaviorForEventTypesHandled(new GetBehavior(bag));
        pcSteve.registerBehaviorForEventTypesHandled(new GetBehavior(pcSteve));

        orc0.registerBehaviorForEventTypesHandled(new GotBehavior(orc0));
        orc1.registerBehaviorForEventTypesHandled(new GotBehavior(orc1));
        bag.registerBehaviorForEventTypesHandled(new GotBehavior(bag));
        pcSteve.registerBehaviorForEventTypesHandled(new GotBehavior(pcSteve));

        orc0.registerBehaviorForEventTypesHandled(new AttackBehavior(orc0));
        orc1.registerBehaviorForEventTypesHandled(new AttackBehavior(orc1));
        bag.registerBehaviorForEventTypesHandled(new AttackBehavior(bag));
        pcSteve.registerBehaviorForEventTypesHandled(new AttackBehavior(pcSteve));

        orc0.registerBehaviorForEventTypesHandled(new AttackedBehavior(orc0));
        orc1.registerBehaviorForEventTypesHandled(new AttackedBehavior(orc1));
        bag.registerBehaviorForEventTypesHandled(new AttackedBehavior(bag));
        pcSteve.registerBehaviorForEventTypesHandled(new AttackedBehavior(pcSteve));
    }

    private static void createTestJMudObjects(JMudObject root){
        orc0 = root.getChildObject("room").getChildObject("orc0");
        orc1 = root.getChildObject("room").getChildObject("orc1");
        bag = root.getChildObject("room").getChildObject("bag");
        pcSteve = root.getChildObject("room").getChildObject("pcSteve");
    }

}