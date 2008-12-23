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
package jmud.test.event;

import jmud.engine.behavior.*;
import jmud.engine.event.*;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;
import jmud.test.TestUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventRegistrationTest{
    private static final int NUM_JOB_WORKERS = 1;

    private JMudEventRegistrar eventRegistrar;
    private JMudObject orc0;
    private JMudObject orc1;
    private JMudObject bag;
    private JMudObject pcSteve;

    @Before
   public void setup(){
       JobManager.getLazyLoadedInstance().init(NUM_JOB_WORKERS);
       eventRegistrar= JMudEventRegistrar.getLazyLoadedInstance();
       loadTestJMudObjects(TestUtil.buildSimpleJMudObjectTree());
       addBehaviorsToJMudObjects();
   }

    // ToDo CM: test for circular subscriptions

    @Test
    public void testEventRegistrationAndUnregistration(){
        JMudObject triggerSourceObject = orc0;
        JMudEventSubscription eventSubscription = new JMudEventSubscription(JMudEventType.Test, triggerSourceObject, orc1);

        eventRegistrar.registerSubscription(eventSubscription);

        Assert.assertFalse("Event not registered", eventRegistrar.getSubscriptionsBySourceObject(triggerSourceObject).isEmpty());

        eventRegistrar.unregisterSubscription(eventSubscription);

        Assert.assertTrue("Event not unregistered", eventRegistrar.getSubscriptionsBySourceObject(triggerSourceObject).isEmpty());
    }

    @After
   public void tearDown(){
       TestUtil.pause(TestUtil.MILLIS_TO_ALLOW_EVENT_COMPLETION);
       JobManager.getLazyLoadedInstance().stopAllWorkers();
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