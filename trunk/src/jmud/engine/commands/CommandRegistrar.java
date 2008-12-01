package jmud.engine.commands;

import java.util.HashMap;

public class CommandRegistrar {
   /**
    * JobManagerHolder is loaded on the first execution of
    * JobManager.getInstance() or the first access to JobManagerHolder.INSTANCE,
    * not before.
    */
   private static class CommandRegistrarHolder {
      private static final CommandRegistrar INSTANCE = new CommandRegistrar();
   }

   public static CommandRegistrar getInstance() {
      return CommandRegistrarHolder.INSTANCE;
   }

   private final HashMap<String, AbstractCommandDef> cmdMap = new HashMap<String, AbstractCommandDef>();

   /*
    * Concrete Class Implementation
    */

   /*
    * Singleton Implementation
    */
   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor
    */
   protected CommandRegistrar() {
   }

   public final void addAbstractCommandDef(final String alias,
         final AbstractCommandDef cmd) {
      synchronized (this.cmdMap) {
         this.cmdMap.put(alias, cmd);
      }
   }

   public final AbstractCommandDef getAbstractCommandDef(final String alias) {
      AbstractCommandDef c = null;
      synchronized (this.cmdMap) {
         c = this.cmdMap.get(alias);
      }
      return c;
   }

   public void init() {

   }

   public final AbstractCommandDef remAbstractCommandDef(final String alias) {
      AbstractCommandDef c = null;
      synchronized (this.cmdMap) {
         c = this.cmdMap.remove(alias);
      }
      return c;
   }

}
