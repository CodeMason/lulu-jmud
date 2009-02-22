package jmud.engine.commands;

import jmud.engine.job.JobManager;
import jmud.engine.job.definitions.AbstractConnectionJob;
import jmud.engine.netIO.Connection;

import java.util.ArrayList;

/**
 * @author david.h.loman
 */
public abstract class AbstractCommand extends AbstractConnectionJob {

	/**
	 * Aliases for the command.
	 */
	protected ArrayList<String> aliases = new ArrayList<String>();

	/**
	 * The string command received from the client, only broken down into an
	 * array by .split(" ")
	 */
	private String[] cmdArray;

	public AbstractCommand(Connection c, String[] cmdArray) {
		super(c);
		this.cmdArray = cmdArray;
	}

	public AbstractCommand(JobManager jm, Connection c, String[] cmdArray) {
		super(jm, c);
		this.cmdArray = cmdArray;
	}

	/**
	 * @return an ArrayList of aliases for the command
	 */
	public final ArrayList<String> getAliases() {
		return aliases;
	}

	public String[] getCmdArray() {
		return cmdArray;
	}

	@Override
	public boolean doJob() {
		boolean retval = true;

		if (this.c == null || this.cmdArray == null) {
			retval = false;
		} else {
			this.c.sendCRLF();
			this.c.sendTextLn(
					"Processing a " + this.getClass().getSimpleName());
			retval = this.doCmd();
		}
		return retval;
	}

	protected abstract boolean doCmd();

}
