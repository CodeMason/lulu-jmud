package jmud.engine.commands;

import java.util.ArrayList;

public abstract class AbstractCommandDef {

   protected String cmdName = "";

   protected ArrayList<String> aliases = new ArrayList<String>();

   public AbstractCommandDef(final String cmdName) {
      this.cmdName = cmdName;
   }

   public abstract boolean doCmd();

   public final ArrayList<String> getAliases() {
      return aliases;
   }

   public final String getCmdName() {
      return cmdName;
   }

}
