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
package jmud.test.job;

import jmud.engine.core.JMudStatics;
import jmud.engine.job.definitions.AbstractDataJob;
import jmud.engine.job.definitions.AbstractJob;
import jmud.engine.job.definitions.ProcessIncomingDataJob;
import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;
import jmud.test.FakeConnection;
import jmud.test.FakeJobManager;
import jmud.test.TestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProcessIncomingDataJobTest {
   private static final int NUM_JOB_WORKERS = 1;
   private static final String TEST_COMMAND = "test command";
   FakeJobManager fakeJobManager = FakeJobManager.getLazyLoadedInstance();
   FakeConnection fakeConnection;
   ProcessIncomingDataJob processIncomingDataJob;

   @Before
   public final void setup() {
      JMudStatics.setDefaultJobManager(fakeJobManager);
      fakeJobManager.init(NUM_JOB_WORKERS);
      setupFakeConnection();
   }

   private void setupFakeConnection() {
      fakeConnection = new FakeConnection();
      fakeConnection.command = TEST_COMMAND;
      fakeConnection.isCommandComplete = true;
   }

   @After
   public final void stopAllWorkers() {
      TestUtil.pause(TestUtil.MILLIS_TO_ALLOW_EVENT_COMPLETION);
      fakeJobManager.stopAllWorkers();
   }

   private void submitProcessIncomingDataJobAndWait(final Connection connection) {
      processIncomingDataJob = new ProcessIncomingDataJob(connection);
      processIncomingDataJob.setJobManager(FakeJobManager
            .getLazyLoadedInstance());
      processIncomingDataJob.submit();
      fakeJobManager.processSubmittedJob();
      TestUtil.pause(TestUtil.MILLIS_TO_ALLOW_EVENT_COMPLETION);
   }

   private void testCreatesSubsequentJobWithCorrectData(
         final ConnectionState connectionState) {
      AbstractJob lastSubmittedJob;
      fakeConnection.setConnState(connectionState);
      submitProcessIncomingDataJobAndWait(fakeConnection);

      lastSubmittedJob = fakeJobManager.getLastSubmittedJob();
      Assert
            .assertTrue("Expected AbstractDataJob not found ",
                  AbstractDataJob.class.isAssignableFrom(lastSubmittedJob
                        .getClass()));
      Assert.assertEquals("Submitted job contains wrong data; expected \""
            + TEST_COMMAND + "\", found "
            + ((AbstractDataJob) lastSubmittedJob).data,
            ((AbstractDataJob) lastSubmittedJob).data, TEST_COMMAND);
   }

   @Test
   public void testCreatesSubsequentJobWithCorrectDataWhenCreatingCharacter() {
      testCreatesSubsequentJobWithCorrectData(ConnectionState.CREATING_CHARACTER);
   }

   @Test
   public void testCreatesSubsequentJobWithCorrectDataWhenLoggedIn() {
      testCreatesSubsequentJobWithCorrectData(ConnectionState.LOGGED_IN);
   }

   @Test
   public void testCreatesSubsequentJobWithCorrectDataWhenLoggedOut() {
      testCreatesSubsequentJobWithCorrectData(ConnectionState.LOGGED_OUT);
   }

   @Test
   public void testCreatesSubsequentJobWithCorrectDataWhenSelectingCharacter() {
      testCreatesSubsequentJobWithCorrectData(ConnectionState.SELECTING_CHARACTER);
   }

   @Test
   public void testNoSubsequentJobCreatedWhenDisconnnected() {
      AbstractJob lastSubmittedJob;
      fakeConnection.setConnState(ConnectionState.DISCONNECTED);
      submitProcessIncomingDataJobAndWait(fakeConnection);

      lastSubmittedJob = fakeJobManager.getLastSubmittedJob();
      Assert.assertEquals(
            "Unexpected job submitted; expected ProcessIncomingDataJob, found "
                  + lastSubmittedJob.getClass().getName(), lastSubmittedJob
                  .getClass(), processIncomingDataJob.getClass());
   }
}
