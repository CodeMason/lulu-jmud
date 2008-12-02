package jmud.engine.character;

import java.util.HashMap;

/**
 * <code>CharacterManager</code> is a singleton patterned class designed to
 * store and facilitate easy lookup of online characters.
 * @author david.h.loman
 */
public class CharacterManager {
   /**
    * <code>CharacterManagerHolder</code> is loaded on the first execution of
    * <code>CharacterManager.getInstance()</code> or the first access to
    * <code>CharacterManagerHolder.INSTANCE</code>, not before.
    */
   private static final class CharacterManagerHolder {
      /**
       * The singleton instance.
       */
      private static final CharacterManager INSTANCE = new CharacterManager();

      /**
       * <code>CharacterManagerHolder</code> is a utility class. Disallowing
       * public/default constructor.
       */
      private CharacterManagerHolder() {

      }
   }

   /**
    * @return the singleton instance
    */
   public static CharacterManager getInstance() {
      return CharacterManagerHolder.INSTANCE;
   }

   private final HashMap<String, Character> nameMap = new HashMap<String, Character>();

   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor.
    */
   protected CharacterManager() {
   }

   /**
    * Add a character.
    * @param c
    *           the character to add
    */
   public final void addCharacter(final Character c) {
      this.nameMap.put(c.getName(), c);

   }

   /**
    * Get a character by name.
    * @param name
    *           the search criteria
    * @return the character as specified by <code>name</code>
    */
   public final Character getCharacterByName(final String name) {
      return this.nameMap.get(name);
   }

   /**
    * Remove a character.
    * @param c
    *           the character to remove
    */
   public final void remCharacter(final Character c) {
      this.nameMap.remove(c.getName());
   }

}
