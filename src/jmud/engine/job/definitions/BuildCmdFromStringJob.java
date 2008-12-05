package jmud.engine.job.definitions;

import jmud.engine.netIO.Connection;

/**
 * Checks a connection's ByteBuffer to see if a valid command has been stored
 * yet. If there has, then this Job will submit a DetermineWhichCommand_Job.
 * 
 * @author David Loman
 * @version 0.1
 */

public class BuildCmdFromStringJob extends AbstractJob {

	private final Connection c;
	private String cmd = "";

	public BuildCmdFromStringJob(final Connection c, final String cmd) {
		this.c = c;
		this.cmd = cmd;
	}

	@Override
	public final boolean doJob() {
		this.c.sendTextLn("Recieved a command: " + this.cmd);
		
		//
		
		
		return true;
	}

	public Connection getC() {
		return c;
	}

	public String getCmd() {
		return cmd;
	}

}
