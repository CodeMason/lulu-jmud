package jmud.test.netIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import jmud.engine.job.JobManager;
import jmud.engine.netIO.ConnectionManager;
import jmud.test.CommonTestMethods;

public class ConnectionManagerTest01 {

	/**
	 * 
	 * This test was designed to test the login, character select, new character and game entrance functions....
	 * 
	 * 
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {

		final int stayAlive = 1000 * 120;

		//initialize JobManager with only 1 worker
		JobManager.getInstance().init(1);
		
		
		ConnectionManager.getInstance().init(InetAddress.getLocalHost(), 54321);
		ConnectionManager.getInstance().start();

		CommonTestMethods.pause(stayAlive);

		System.out.println("Test timeout reached(" + (stayAlive / 1000) + " secs).  Shutting down.");
		ConnectionManager.getInstance().stop();
	}

}
