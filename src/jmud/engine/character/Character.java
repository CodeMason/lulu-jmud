package jmud.engine.character;

import java.util.HashMap;

import jmud.engine.core.Targetable;
import jmud.engine.dbio.Persistable;
import jmud.engine.item.ItemContainer;
import jmud.engine.stats.StatMap;

/**
 * 24NOV08: Character objects should only represent the data that pertains to an
 * individual character... Attribute, Name, Description, etc Created on 25NOV08
 */
public class Character implements Targetable, Persistable {
   private final int charID;

   // info
   private final String name;
   private final String description;
   private String prompt;

   // stats
   private final StatMap stats = new StatMap();

   // 'Slots'
   private final HashMap<String, ItemContainer> slots = new HashMap<String, ItemContainer>();

   // location
   // private Room room;

   public Character(final int iID, final String name, final String desc) {
      this.charID = iID;
      this.name = name;
      this.description = desc;
   }

   public final int getCharID() {
      return charID;
   }

   public final String getDescription() {
      return description;
   }

   public final String getName() {
      return name;
   }

   public final String getPrompt() {
      return prompt;
   }

   public final HashMap<String, ItemContainer> getSlots() {
      return slots;
   }

   public final StatMap getStatMap() {
      return stats;
   }

}
