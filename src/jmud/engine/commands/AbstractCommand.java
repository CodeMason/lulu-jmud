package jmud.engine.commands;

import jmud.engine.job.definitions.AbstractJob;
import jmud.engine.netIO.Connection;

import java.util.ArrayList;

/**
 * @author david.h.loman
 */
public abstract class AbstractCommand extends AbstractJob {

	/**
	 * Aliases for the command.
	 */
	protected ArrayList<String> aliases = new ArrayList<String>();

	private Connection conn;
	private String[] cmdArray;

	/**
    * 
    */
	public AbstractCommand(Connection c, String[] cmdArray) {
		this.conn = c;
		this.cmdArray = cmdArray;
	}

	/**
	 * @return an ArrayList of aliases for the command
	 */
	public final ArrayList<String> getAliases() {
		return aliases;
	}

	public Connection getConn() {
		return conn;
	}

	public String[] getCmdArray() {
		return cmdArray;
	}

	@Override
	public boolean doJob() {
		boolean retval = true;

		if (this.conn == null || this.cmdArray == null) {
			retval = false;
		} else {
			this.getConn().sendCRLF();
			this.getConn().sendTextLn("Processing a " + this.getClass().getSimpleName());
			retval = this.doCmd();
		}
		return retval;
	}

	protected abstract boolean doCmd();

	public abstract AbstractCommand clone(Connection c, String[] cmdArray);
}
