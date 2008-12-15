package jmud.test.event;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventRegistrar;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;
import jmud.test.TestUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventBehaviorPerformanceTest{
    private static final int NUM_JOB_WORKERS = 10;
    private static final int NUM_PARENT_OBJECTS = 25;
    private static final int MILLIS_TO_ALLOW_WORKER_WAKEUP = 250;
    private static final int MILLIS_TO_ALLOW_EVENT_COMPLETION = 1000;

    private JMudObject root;

   @Before
   public void setup(){
       JobManager.getLazyLoadedInstance().init(NUM_JOB_WORKERS);
       JMudEventRegistrar.getLazyLoadedInstance();
       root = TestUtil.buildObjectTree(NUM_PARENT_OBJECTS);
   }

   @Test
   public void testGetEventChangesJMudObjectParent() {
       JMudObject orc1 = getParentObjectFromObjectTree(1);
       JMudObject orc2 = getParentObjectFromObjectTree(2);
       JMudObject orc2Gear1 = getChildObjectFromParentObject(orc2, 1);

      submitTimeBufferedEvent(new JMudEvent(JMudEventType.Get, orc1, orc2Gear1));

      Assert.assertEquals("GetEvent: JMudObject \"" + orc2Gear1.toStringShort() +
                          "\" was not transferred from JMudObject \"" + orc1.toStringShort() +
                          "\" to JMudObject \"pcSteve\"",
                          orc2Gear1.getParentObject(), orc1);
   }

   private JMudObject getParentObjectFromObjectTree(int indexOfParentObject){
       return (JMudObject) root.getChildObject("room").getChildObjects().values().toArray()[indexOfParentObject];
   }

   private JMudObject getChildObjectFromParentObject(JMudObject parentObject, int indexOfChildObject){
       return (JMudObject) parentObject.getChildObjects().values().toArray()[indexOfChildObject];
   }

   @After
   public void tearDown(){
       TestUtil.pause(MILLIS_TO_ALLOW_EVENT_COMPLETION);
       JobManager.getLazyLoadedInstance().stopAllWorkers();
   }

    private void submitTimeBufferedEvent(JMudEvent getEvent){
        TestUtil.pause(MILLIS_TO_ALLOW_WORKER_WAKEUP);
        getEvent.submitSelf();
        TestUtil.pause(MILLIS_TO_ALLOW_EVENT_COMPLETION);
    }
}