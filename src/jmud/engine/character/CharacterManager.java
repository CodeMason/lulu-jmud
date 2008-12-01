package jmud.engine.character;

import java.util.HashMap;

/**
 * CharacterManager is a Singleton patterned class designed to store and
 * facilitate easy lookup of Online Characters.
 */

public class CharacterManager {
   /*
    * Singleton Implementation
    */

   /**
    * CharacterManagerHolder is loaded on the first execution of
    * CharacterManager.getInstance() or the first access to
    * CharacterManagerHolder.INSTANCE, not before.
    */
   private static class CharacterManagerHolder {
      private static final CharacterManager INSTANCE = new CharacterManager();
   }

   public static CharacterManager getInstance() {
      return CharacterManagerHolder.INSTANCE;
   }

   private final HashMap<String, Character> nameMap = new HashMap<String, Character>();

   /*
    * Concrete Class Implementation
    */

   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor
    */
   protected CharacterManager() {
   }

   public final void addCharacter(final Character c) {
      this.nameMap.put(c.getName(), c);

   }

   public final Character getCharacterByName(final String name) {
      return this.nameMap.get(name);
   }

   public final void remCharacter(final Character c) {
      this.nameMap.remove(c.getName());
   }

}
