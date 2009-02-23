package jmud.engine.character;

import java.util.HashMap;
import java.util.Set;

import jmud.engine.character.PlayerCharacter;
import jmud.engine.dbio.util.SqlConnHelpers;

/**
 * <code>CharacterManager</code> is a singleton patterned class designed to
 * store and facilitate easy lookup of online characters.
 * @author david.h.loman
 */
public class PlayerCharacterManager {
   /**
    * <code>Holder</code> is loaded on the first execution of
    * <code>CharacterManager.getInstance()</code> or the first access to
    * <code>Holder.INSTANCE</code>, not before.
    */
   private static final class Holder {
      /**
       * The singleton instance.
       */
      private static final PlayerCharacterManager INSTANCE = new PlayerCharacterManager();

      /**
       * <code>Holder</code> is a utility class. Disallowing
       * public/default constructor.
       */
      private Holder() {

      }
   }

   /**
    * @return the singleton instance
    */
   public static PlayerCharacterManager getInstance() {
      return Holder.INSTANCE;
   }

   private final HashMap<String, PlayerCharacter> nameMap = new HashMap<String, PlayerCharacter>();

   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor.
    */
   protected PlayerCharacterManager() {
   }



   
   
   
   
   
   
   
   
   
   
   
	/**
	 * Load a PlayerCharacter.
	 * 
	 * @param pcName
	 *            the character name of the PlayerCharacter to lookup
	 * 
	 * @return Either a reference to an PlayerCharacter object if the DB lookup is a
	 *         success, or NULL if the DB lookup fails.
	 */
	public PlayerCharacter loadPlayerCharacter(String pcName, int ownerAccountID) {
		// Get the PlayerCharacter data from the database
		PlayerCharacter pc = SqlConnHelpers.getPlayerCharacter(pcName, ownerAccountID);

		
		if (pc == null) {
			return null;
		} else {
			this.nameMap.put(pc.getPlayerCharactersName(), pc);
			return pc;
		}
	}

	/**
	 * Unload an PlayerCharacter.
	 * 
	 * @param pc
	 *            PlayerCharacter object reference to be unloaded
	 * 
	 * @return boolean value showing whether or not the PlayerCharacter object was
	 *         unloaded from the PlayerCharacterManager successfully.
	 */
	public boolean unloadAccont(PlayerCharacter pc) {
		return this.unloadPlayerCharacter(pc.getPlayerCharactersName());
	}

	/**
	 * Unload an PlayerCharacter.
	 * 
	 * @param PlayerCharacterID
	 *            Integer value representing the ID of the PlayerCharacter object to be
	 *            unloaded.
	 * 
	 * @return boolean value showing whether or not the PlayerCharacter object was
	 *         unloaded from the PlayerCharacterManager successfully.
	 */
	public boolean unloadPlayerCharacter(String pcName) {
		PlayerCharacter pc = this.nameMap.remove(pcName);
		if (pc == null) {
			System.err.println("Failed to obtain PlayerCharacter object reference during unloadPlayerCharacter()");
			return true;
		}
		if (pc.save() == false) {
			System.err.println("PlayerCharacter failed to .save() during unloadPlayerCharacter()!!!!!");
			return true;
		}

		return true;
	}

	/**
	 * Gets a reference to a PlayerCharacter object.
	 * 
	 * @param pcName
	 *            Integer value representing the ID of the PlayerCharacter object to be returned.
	 * 
	 * @return Either a reference to an PlayerCharacter object if the PlayerCharacterManager lookup is a
	 *         success, or NULL if the PlayerCharacterManager lookup fails.
	 */
	public PlayerCharacter getPlayerCharacter(String pcName) {
		return this.nameMap.get(pcName);
	}
	
	
	public Set<String> getAllPlayersOnline() {
		return this.nameMap.keySet();
	}

}
