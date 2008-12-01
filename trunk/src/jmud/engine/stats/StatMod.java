package jmud.engine.stats;

/**
 * Represents a modification to a Stat. Contains a name for ease of look up and
 * a category for Mod filtering.
 * @author David Loman
 * @version 0.1
 */

public class StatMod {
   private String name = "";
   private int value;
   private String category = "";

   public StatMod(final String category, final String name, final int value) {
      super();
      this.category = category;
      this.name = name;
      this.value = value;
   }

   public final String getCategory() {
      return category;
   }

   public final String getName() {
      return name;
   }

   public final int getValue() {
      return value;
   }

   public final void setCategory(final String category) {
      this.category = category;
   }

   public final void setName(final String name) {
      this.name = name;
   }

   public final void setValue(final int value) {
      this.value = value;
   }

}
