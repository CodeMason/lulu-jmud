package jmud.engine.commands.definitions;

import jmud.engine.commands.AbstractCommand;
import jmud.engine.netIO.Connection;

public class QuitCommand extends AbstractCommand {

	//Use this constructor to initialize the object that will be stored
	//in the CommandFactory
	public QuitCommand() {
		this(null,null);
	}

    // ToDo CM: as per http://jmud.org/phpbb/viewtopic.php?t=5&sid=49f002405ba93f8d512f96165c9bcb6b
    //          I'd like to see aliases separated from commands
    /**
	 * Use this Constructor to create a fully functional Command
	 * @param c
	 * @param cmdArray
	 */
	public QuitCommand(Connection c, String[] cmdArray) {
		super(c, cmdArray);
		this.getAliases().add("quit");
		this.getAliases().add("qui");
		this.getAliases().add("qu");
		this.getAliases().add("q");

	}



	@Override
	protected boolean doCmd() {


		//TODO eventually, lets send them back to character select.
		this.getConn().disconnect();

		return false;
	}


	@Override
	public QuitCommand clone(Connection c, String[] cmdArray) {
		return new QuitCommand(c,cmdArray);
	}



}