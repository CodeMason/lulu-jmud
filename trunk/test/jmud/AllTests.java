package jmud;

import jmud.netIO.CommandBufferTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for jmud");
		//$JUnit-BEGIN$
		suite.addTestSuite(CommandBufferTest.class);

		//$JUnit-END$
		return suite;
	}

}
