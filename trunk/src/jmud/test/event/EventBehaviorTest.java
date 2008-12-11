package jmud.test.event;

import jmud.engine.behavior.GetBehavior;
import jmud.engine.behavior.GotBehavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventRegistrar;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;
import jmud.test.CommonTestMethods;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventBehaviorTest{
    private static final int NUM_JOB_WORKERS = 1;

    private JMudObject bag;
    private JMudObject pcSteve;

   @Before
   public void setup(){
       JobManager.getLazyLoadedInstance().init(NUM_JOB_WORKERS);
       JMudEventRegistrar.getLazyLoadedInstance().init();
       loadTestJMudObjects(CommonTestMethods.buildSimpleJMudObjectTree());
       addBehaviorsToJMudObjects();
   }

   @Test
   public void testGetEvent() {

      JMudEvent getEvent = new JMudEvent(JMudEventType.Get, pcSteve, bag);

      // short sleep so we can test 'waking' a JobWorker
      CommonTestMethods.pause(250);

      getEvent.submitSelf();

      // ensure all events are processed
      CommonTestMethods.pause(1000);

      Assert.assertEquals("GetEvent: JMudObject \"bag\" was not transferred from JMudObject \"room\" to JMudObject \"pcSteve\"", bag.getParent(), pcSteve);

      // ensure all events are processed
      CommonTestMethods.pause(1000);
   }

   @After
   public void tearDown(){
       JobManager.getLazyLoadedInstance().stopAllWorkers();
   }

    private void loadTestJMudObjects(JMudObject root){
        bag = root.childrenGet("room").childrenGet("bag");
        pcSteve = root.childrenGet("room").childrenGet("pcSteve");
    }

    private void addBehaviorsToJMudObjects(){
        bag.addEventBehavior(new GetBehavior(bag));
        bag.addEventBehavior(new GotBehavior(bag));
        pcSteve.addEventBehavior(new GotBehavior(pcSteve));
    }

}
