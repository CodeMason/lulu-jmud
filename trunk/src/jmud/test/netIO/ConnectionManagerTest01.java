package jmud.test.netIO;

import jmud.engine.job.JobManager;
import jmud.engine.netIO.ConnectionManager;
import jmud.test.TestUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionManagerTest01 {

	/**
	 *
	 * This test was designed to test the login, character select, new character and game entrance functions....
	 *
	 * // ToDo CM: writing a unit test for this is going to fun. :(
     *
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {

		final int stayAlive = 1000 * 120;

		//initialize JobManager with only 1 worker
		JobManager.getLazyLoadedInstance().init(1);


		ConnectionManager.getLazyLoadedInstance().init(InetAddress.getLocalHost(), 54321);
		ConnectionManager.getLazyLoadedInstance().start();

		TestUtil.pause(stayAlive);

		System.out.println("Test timeout reached(" + (stayAlive / 1000) + " secs).  Shutting down.");
		ConnectionManager.getLazyLoadedInstance().stop();
		JobManager.getLazyLoadedInstance().stopAllWorkers();
	}

}
