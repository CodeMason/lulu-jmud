package jmud.engine.core;

/**
 * All static data, such as data that need not be duplicated and contained
 * within individual objects should go here. Ultimately, settings file loaders
 * and saver methods will reside here to allow for easy jMUD config.
 * @author David Loman
 */
public final class Settings {

   /**
    * Carriage return, line feed.
    */
   public static final String CRLF = "\n\r";

   /**
    * <code>Settings</code> is a utility class. Disallowing public/default
    * constructor.
    */
   private Settings() {

   }
}
