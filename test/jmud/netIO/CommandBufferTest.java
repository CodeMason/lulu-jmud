package jmud.netIO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;

import jmud.TestUtil;
import jmud.engine.core.JMudStatics;
import jmud.engine.job.JobManager;
import jmud.engine.netio.CommandBuffer;
import jmud.engine.netio.JMudClient;
import jmud.engine.netio.JMudClientManager;
import junit.framework.TestCase;

import org.junit.Test;

public class CommandBufferTest extends TestCase {
	private CommandBuffer cb;
	private final int portToTestOn = 54321;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.cb = new CommandBuffer();
	}

	@Test
	public void testWriteByteArray() {
		String inCmd = "Testing" + JMudStatics.CRNL + "nextCommand";

		try {
			cb.write(inCmd.getBytes());
		} catch (IOException e) {
			fail("Threw IOException: " + e.getLocalizedMessage());
		}

		cb.parseCommands();

		assertEquals("Testing.cmdCount().", 1, cb.cmdCount());
		assertTrue("Testing hasNextCommand().", cb.hasNextCommand());
		assertEquals("Testing getNextCommand().", "Testing", cb.getNextCommand());
		assertFalse("Testing hasNextCommand().", cb.hasNextCommand());
	}

	@Test
	public void testWriteByteArrayInt() {
		String inCmd = "Testing" + JMudStatics.CRNL + "nextCommand" + JMudStatics.CRNL;

		try {
			cb.write(inCmd.getBytes(), 10);
		} catch (IOException e) {
			fail("Threw IOException: " + e.getLocalizedMessage());
		}

		cb.parseCommands();

		assertEquals("Testing.cmdCount().", 1, cb.cmdCount());
		assertTrue("Testing hasNextCommand().", cb.hasNextCommand());
		assertEquals("Testing getNextCommand().", "Testing", cb.getNextCommand());
		assertFalse("Testing hasNextCommand().", cb.hasNextCommand());

	}

	@Test
	public void testReadFromSocketChannel() {

		// Startup a JobManager
		JobManager.getInstance().init(2);
		JMudClientManager jmcm = null;

		try {
			jmcm = new JMudClientManager(this.portToTestOn);
		} catch (IOException e) {
			fail("JMudClientManager failed to start.");
			JobManager.getInstance().stopAllWorkers();
			return;
		}
		// Startup a JMudClientManager
		jmcm.start();

		// Instantiate and connect a DummyClient
		DummyClient dc = null;
		try {
			dc = new DummyClient();
			dc.connect(InetAddress.getLocalHost(), this.portToTestOn);

			TestUtil.pause(250L);

			// We should be expecting the Login screen here.
			while (dc.getDis().available() > 0) {
				dc.getDis().read();
			}

			// Get a handle on the CommandBuffer
			JMudClient jmc = (JMudClient) jmcm.getAllJMudClients().toArray()[0];
			this.cb = jmc.getCmdBuffer();

			// Try to send a command to the MUD
			dc.getDos().write(("TestingCMD" + JMudStatics.CRNL).getBytes());
		} catch (IOException e) {
			fail("DummyClient failed to send data.");
			try {
				dc.close();
			} catch (IOException e1) {
				fail("DummyClient failed to close.");
			}
			TestUtil.pause(250L);
			jmcm.stop();
			JobManager.getInstance().stopAllWorkers();
		}
		TestUtil.pause(250L);

		String response = "";
		try {
			// Compile the response
			while (dc.getDis().available() > 0) {
				response += (char) dc.getDis().read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue("Seeing if response contains string:", response.contains("Make a selection:"));
		try {
			dc.close();
		} catch (IOException e1) {
			fail("DummyClient failed to close.");
		}
		TestUtil.pause(250L);
		jmcm.stop();
		JobManager.getInstance().stopAllWorkers();
	}

	@Test
	public void testReadFromInputStream() {
		String inCmd = "Testing" + JMudStatics.CRNL + "nextCommand";

		ByteArrayInputStream bias = new ByteArrayInputStream(inCmd.getBytes());

		try {
			this.cb.readFrom(bias);
		} catch (IOException e) {
			fail("CommandBuffer.readFrom(InputStream) threw an IOException");
		}

		cb.parseCommands();

		assertEquals("Testing.cmdCount().", 1, cb.cmdCount());
		assertTrue("Testing hasNextCommand().", cb.hasNextCommand());
		assertEquals("Testing getNextCommand().", "Testing", cb.getNextCommand());
		assertFalse("Testing hasNextCommand().", cb.hasNextCommand());

	}

	@Test
	public void testToString() {
		String inCmd = "Testing" + JMudStatics.CRNL + "nextCommand";

		try {
			cb.write(inCmd.getBytes());
		} catch (IOException e) {
			fail("CommandBuffer.write(byte[]) threw an IOException");
		}

		cb.parseCommands();

		String output = cb.toString();
		System.out.println(output);

		String expected = "CharacterBuffer is: 11 characters long.\nCompleted Command Queue:\n\t-Testing\n";
		assertTrue("Testing toString().", output.equals(expected));
	}

}
