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
import jmud.engine.job.definitions.SplashScreenJob;
import jmud.engine.job.JobManager;
import jmud.engine.netIO.Connection;
import jmud.test.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

public class SplashScreenJobTest {
    private static final int NUM_JOB_WORKERS = 1;

    @Before
    public void startJobManager() {
        JobManager.getLazyLoadedInstance().init(NUM_JOB_WORKERS);
    }

    @Test
    public void testSendingSpashScreen() {
        FakeConnection fakeConnection = new FakeConnection();
        submitSplashScreenJobAndWait(fakeConnection);

        Assert.assertNotNull("Splash screen not sent", fakeConnection.lastSentText);
        Assert.assertEquals("Splash screen not sent correctly", fakeConnection.lastSentText, JMudStatics.SplashScreen);
    }

    @After
    public void stopAllWorkers() {
        TestUtil.pause(TestUtil.MILLIS_TO_ALLOW_EVENT_COMPLETION);
        JobManager.getLazyLoadedInstance().stopAllWorkers();
    }

    private void submitSplashScreenJobAndWait(FakeConnection fakeConnection) {
        SplashScreenJob splashScreenJob = new SplashScreenJob(fakeConnection);
        splashScreenJob.submit();
        TestUtil.pause(TestUtil.MILLIS_TO_ALLOW_EVENT_COMPLETION);
    }

    private class FakeConnection extends Connection {
        String lastSentText;

        public FakeConnection() {
            super(null, null);
        }

        public void sendText(String textToSend) {
            lastSentText = textToSend;
        }
    }
}
