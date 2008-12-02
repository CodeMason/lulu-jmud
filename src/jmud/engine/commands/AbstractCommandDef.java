package jmud.engine.commands;

import java.util.ArrayList;

/**
 * @author david.h.loman
 */
public abstract class AbstractCommandDef {

   /**
    * Aliases for the command.
    */
   protected ArrayList<String> aliases = new ArrayList<String>();

   protected String cmdName = "";

   /**
    * @param cmdName
    *           the set command name
    */
   public AbstractCommandDef(final String cmdName) {
      this.cmdName = cmdName;
   }

   /**
    * @return true at this time
    */
   public abstract boolean doCmd();

   /**
    * @return an ArrayList of aliases for the command
    */
   public final ArrayList<String> getAliases() {
      return aliases;
   }

   /**
    * @return the command name in String form
    */
   public final String getCmdName() {
      return cmdName;
   }

}
