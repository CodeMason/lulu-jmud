package jmud.engine.commands.definitions;

import jmud.engine.commands.AbstractCommand;
import jmud.engine.job.JobManager;
import jmud.engine.netio.JMudClient;
import jmud.engine.netio.JMudClientState;

public class QuitCommand extends AbstractCommand {

	// Use this constructor to initialize the object that will be stored
	// in the CommandFactory
	public QuitCommand() {
		this(null, null);
	}

	// ToDo CM: as per
	// http://jmud.org/phpbb/viewtopic.php?t=5&sid=49f002405ba93f8d512f96165c9bcb6b
	// I'd like to see aliases separated from commands
	//
	// TODO DHL: Assuming you mean *user* aliases, I agree. Built in aliases
	// should remain here, in the class definition so that they can be
	// registered with the CommandManager

	/**
	 * Use this Constructor to create a fully functional Command
	 * 
	 * @param c
	 * @param cmdArray
	 */
	public QuitCommand(JMudClient c, String[] cmdArray) {
		super(c, cmdArray);

	}
	public QuitCommand(JobManager jm, JMudClient c, String[] cmdArray) {
		super(jm, c, cmdArray);
	}
	
	@Override
	protected void setAliases() {
		this.getAliases().add("quit");
		this.getAliases().add("qui");
		this.getAliases().add("qu");
		this.getAliases().add("q");
	}

	@Override
	protected boolean doCmd() {
		// Change the character state from INGAME back to CHARACTERMANAGER
		this.c.changeConnState(JMudClientState.CHARACTERMANAGE);

		return true;
	}

	@Override
	public AbstractCommand getNewInstance(JMudClient c, JobManager jm,
			String[] cmdArray) {
		return new QuitCommand(jm,c,cmdArray);
	}


}
