package jmud.test.event;

import jmud.engine.behavior.*;
import jmud.engine.event.*;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;
import jmud.test.CommonTestMethods;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventBehaviorTest{
    private static final int NUM_JOB_WORKERS = 1;
    private static final int MILLIS_TO_ALLOW_WORKER_WAKEUP = 250;
    private static final int MILLIS_TO_ALLOW_EVENT_COMPLETION = 1000;

    private JMudObject orc0;
    private JMudObject orc1;
    private JMudObject bag;
    private JMudObject pcSteve;

    @Before
   public void setup(){
       JobManager.getLazyLoadedInstance().init(NUM_JOB_WORKERS);
       JMudEventRegistrar.getLazyLoadedInstance();
       loadTestJMudObjects(CommonTestMethods.buildSimpleJMudObjectTree());
       addBehaviorsToJMudObjects();
   }

   @Test
   public void testGetEventChangesJMudObjectParent() {

      submitTimeBufferedEvent(new JMudEvent(JMudEventType.Get, pcSteve, bag));

      Assert.assertEquals("GetEvent: JMudObject \"bag\" was not transferred from JMudObject \"room\" to JMudObject \"pcSteve\"", bag.getParentObject(), pcSteve);
   }

   @Test
   public void testEventTriggersOnCorrectTarget(){
       JMudObject triggerTargetObject = bag;
       JMudObject eventTargetObject = bag;

       TriggerBehavior triggerBehavior = new TriggerBehavior(orc0, JMudEventType.Get, JMudEventParticipantRole.TARGET, triggerTargetObject, JMudEventParticipantRole.SOURCE, JMudEventType.Test);
       orc0.registerBehaviorForEventTypesHandled(triggerBehavior);
       pcSteve.registerBehaviorForEventTypesHandled(new TestBehavior(pcSteve));

       submitTimeBufferedEvent(new JMudEvent(JMudEventType.Get, pcSteve, eventTargetObject));

       Assert.assertTrue("Event did not fire trigger event on correct target", TestBehavior.hasBehaviorBeenCalled());
   }

    @Test
    public void testEventDoesNotTriggerOnIncorrectTarget(){
        JMudObject triggerTargetObject = bag;
        JMudObject eventTargetObject = orc0;

        TriggerBehavior triggerBehavior = new TriggerBehavior(orc0, JMudEventType.Get, JMudEventParticipantRole.TARGET, triggerTargetObject, JMudEventParticipantRole.SOURCE, JMudEventType.Test);
        orc0.registerBehaviorForEventTypesHandled(triggerBehavior);
        pcSteve.registerBehaviorForEventTypesHandled(new TestBehavior(pcSteve));

        submitTimeBufferedEvent(new JMudEvent(JMudEventType.Get, pcSteve, eventTargetObject));

        Assert.assertFalse("Event fired trigger event on wrong target", TestBehavior.hasBehaviorBeenCalled());
    }

    @Test
    public void testEventTriggersOnCorrectSource(){
        JMudObject triggerSourceObject = pcSteve;
        JMudObject eventSourceObject = pcSteve;

        TriggerBehavior triggerBehavior = new TriggerBehavior(orc0, JMudEventType.Get, JMudEventParticipantRole.SOURCE, triggerSourceObject, JMudEventParticipantRole.SOURCE, JMudEventType.Test);
        orc0.registerBehaviorForEventTypesHandled(triggerBehavior);
        pcSteve.registerBehaviorForEventTypesHandled(new TestBehavior(eventSourceObject));

        submitTimeBufferedEvent(new JMudEvent(JMudEventType.Get, eventSourceObject, bag));

        Assert.assertTrue("Event did not fire trigger event on correct source", TestBehavior.hasBehaviorBeenCalled());
    }

    @Test
    public void testEventDoesNotTriggerOnIncorrectSource(){
        JMudObject triggerSourceObject = pcSteve;
        JMudObject eventSourceObject = orc1;

        TriggerBehavior triggerBehavior = new TriggerBehavior(orc0, JMudEventType.Get, JMudEventParticipantRole.SOURCE, triggerSourceObject, JMudEventParticipantRole.SOURCE, JMudEventType.Test);
        orc0.registerBehaviorForEventTypesHandled(triggerBehavior);
        pcSteve.registerBehaviorForEventTypesHandled(new TestBehavior(pcSteve));

        submitTimeBufferedEvent(new JMudEvent(JMudEventType.Get, eventSourceObject, bag));

        Assert.assertFalse("Event fired trigger event on wrong source", TestBehavior.hasBehaviorBeenCalled());
    }

    @Test
    public void testEventTriggersAtCorrectTarget(){
        JMudObject triggerTargetObject = bag;
        JMudObject eventTargetObject = bag;

        TriggerBehavior triggerBehavior = new TriggerBehavior(orc0, JMudEventType.Get, JMudEventParticipantRole.TARGET, triggerTargetObject, JMudEventParticipantRole.TARGET, JMudEventType.Test);
        orc0.registerBehaviorForEventTypesHandled(triggerBehavior);
        pcSteve.registerBehaviorForEventTypesHandled(new TestBehavior(triggerTargetObject));

        submitTimeBufferedEvent(new JMudEvent(JMudEventType.Get, pcSteve, eventTargetObject));

        Assert.assertTrue("Event did not fire trigger event at target", TestBehavior.hasBehaviorBeenCalled());
    }

    @Test
    public void testEventDoesNotTriggerAtIncorrectTarget(){
        JMudObject triggerTargetObject = bag;
        JMudObject eventTargetObject = bag;

        TriggerBehavior triggerBehavior = new TriggerBehavior(orc0, JMudEventType.Get, JMudEventParticipantRole.TARGET, triggerTargetObject, JMudEventParticipantRole.TARGET, JMudEventType.Test);
        orc0.registerBehaviorForEventTypesHandled(triggerBehavior);
        pcSteve.registerBehaviorForEventTypesHandled(new TestBehavior(pcSteve));

        submitTimeBufferedEvent(new JMudEvent(JMudEventType.Get, pcSteve, eventTargetObject));

        Assert.assertFalse("Event fired trigger event at wrong target", TestBehavior.hasBehaviorBeenCalled());
    }

    @After
   public void tearDown(){
       CommonTestMethods.pause(MILLIS_TO_ALLOW_EVENT_COMPLETION);
       JobManager.getLazyLoadedInstance().stopAllWorkers();
   }


    private void submitTimeBufferedEvent(JMudEvent getEvent){
        CommonTestMethods.pause(MILLIS_TO_ALLOW_WORKER_WAKEUP);
        getEvent.submitSelf();
        CommonTestMethods.pause(MILLIS_TO_ALLOW_EVENT_COMPLETION);
    }

    private void loadTestJMudObjects(JMudObject root){
        orc0 = root.getChildObject("room").getChildObject("orc0");
        orc1 = root.getChildObject("room").getChildObject("orc1");
        bag = root.getChildObject("room").getChildObject("bag");
        pcSteve = root.getChildObject("room").getChildObject("pcSteve");
    }

    private void addBehaviorsToJMudObjects(){
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
}
