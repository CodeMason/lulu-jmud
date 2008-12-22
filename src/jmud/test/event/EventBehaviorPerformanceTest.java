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