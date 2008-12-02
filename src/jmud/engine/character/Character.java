package jmud.engine.character;

import java.util.HashMap;

import jmud.engine.core.Targetable;
import jmud.engine.dbio.Persistable;
import jmud.engine.item.ItemContainer;
import jmud.engine.stats.StatMap;

/**
 * <code>Character</code> objects should only represent the data that pertains
 * to an individual character... attribute, name, description, etc.
 * @author david.h.loman
 */
public class Character implements Targetable, Persistable {
   private final int charID;

   private final String description;
   /**
    * Info.
    */
   private final String name;
   private String prompt;

   /**
    * Slots.
    */
   private final HashMap<String, ItemContainer> slots = new HashMap<String, ItemContainer>();

   /**
    * Stats.
    */
   private final StatMap stats = new StatMap();

   // location
   // private Room room;

   /**
    * Explicit constructor.
    * @param iID
    *           the ID
    * @param inName
    *           the character's name
    * @param desc
    *           the description of the character
    */
   public Character(final int iID, final String inName, final String desc) {
      this.charID = iID;
      this.name = inName;
      this.description = desc;
   }

   /**
    * @return the character ID
    */
   public final int getCharID() {
      return charID;
   }

   /**
    * @return the character's description
    */
   public final String getDescription() {
      return description;
   }

   /**
    * @see jmud.engine.core.Targetable#getName()
    * @return the character's name
    */
   public final String getName() {
      return name;
   }

   /**
    * @return the system prompt
    */
   public final String getPrompt() {
      return prompt;
   }

   /**
    * @return the HashMaps of slots for a container
    */
   public final HashMap<String, ItemContainer> getSlots() {
      return slots;
   }

   /**
    * @return the StatMap
    * @see jmud.engine.core.Targetable#getStatMap()
    */
   public final StatMap getStatMap() {
      return stats;
   }

}
