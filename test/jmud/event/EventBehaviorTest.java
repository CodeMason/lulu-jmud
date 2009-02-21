/**
 * This file is part of Lulu's JMud.
 *
 *  Lulu's JMud is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Lulu's JMud is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Lulu's JMud.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmud.event;

import jmud.engine.behavior.*;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventParticipantRole;
import jmud.engine.event.JMudEventRegistrar;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;
import jmud.TestUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class EventBehaviorTest{
    private static final int NUM_JOB_WORKERS = 1;

    private JMudObject orc0;
    private JMudObject orc1;
    private JMudObject bag;
    private JMudObject pcSteve;

    @Before
   public void setup(){
       JobManager.getLazyLoadedInstance().init(NUM_JOB_WORKERS);
       JMudEventRegistrar.getLazyLoadedInstance();
       loadTestJMudObjects(TestUtil.buildSimpleJMudObjectTree());
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
       TestUtil.pause(TestUtil.MILLIS_TO_ALLOW_EVENT_COMPLETION);
       JobManager.getLazyLoadedInstance().stopAllWorkers();
   }


    private void submitTimeBufferedEvent(JMudEvent getEvent){
        TestUtil.pause(TestUtil.MILLIS_TO_ALLOW_WORKER_WAKEUP);
        getEvent.selfSubmit();
        TestUtil.pause(TestUtil.MILLIS_TO_ALLOW_EVENT_COMPLETION);
    }

    private void loadTestJMudObjects(JMudObject root){
        orc0 = root.getChildObject("room").getChildObject("orc0");
        orc1 = root.getChildObject("room").getChildObject("orc1");
        bag = root.getChildObject("room").getChildObject("bag");
        pcSteve = root.getChildObject("room").getChildObject("pcSteve");
    }

    private void addBehaviorsToJMudObjects(){
        List<JMudObject> objectsNeedingBehaviors = Arrays.asList(orc0, orc1, bag, pcSteve);
        List<Class> behaviorClassesToAdd = Arrays.asList((Class) GetBehavior.class, GotBehavior.class, AttackBehavior.class, AttackedBehavior.class);

        for(JMudObject objectNeedingBehavior : objectsNeedingBehaviors){
            for(Behavior behavior : BehaviorFactory.createBehaviors(behaviorClassesToAdd, objectNeedingBehavior)){
                objectNeedingBehavior.registerBehaviorForEventTypesHandled(behavior);
            }
        }
    }
}
