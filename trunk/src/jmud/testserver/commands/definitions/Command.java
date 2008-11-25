package jmud.testserver.commands.definitions;

import java.util.ArrayList;

/**
 * Command is the abstract definition of an executable action.
 * <p/>
 * Commands are passed to the MUD engine for processing
 *
 * Created on March 14 2003 10:17 PM
 */
public abstract class Command {

	private String baseCmd  = "";


    /*
      (Note: after reading this again in Nov '09 I don't really have a clue what I was talking about :D) 

      This is what I was going to do:
      create the command with everything you could possibly need
      in a command, that way, exec can be sure that it has what it needs

      BUT:

      Since we know what a command is when we create it, why do
      we have to pass it everything?
      Why can't that particular Command subclass's exec function
      check to make sure that everything is there?
      Sure we won't be able to check the command BEFORE hand, but that's ok.

      AHA! Each Command subclass can have a CheckCommandParams() method that
      will verify that everything that is needed is there
      BUT, we don't really want to check that stuff until we get to the engine.

      All we can really pass in, without player input is the Player and the Room.
      We know who's issuing the command and we know where they are.
    */


    /**
     * Each subclass of Command must implement its own exec() method because
     * that's what commands are for, executing specific MUD commands.
     */
    public abstract boolean exec();

    /** return the list of commands this command should respond to */
    //protected static String[] names(){ return new String[]{""}; }
    protected ArrayList<String> aliases = new ArrayList<String>();
    public ArrayList<String> getAliases() {
    	return this.aliases;
    }
    public boolean hasAlias(String alias) {
    	return this.aliases.contains(alias);
    }
    
    
    
	public String getBaseCmd() {
		return baseCmd;
	}
    
    
    
}

