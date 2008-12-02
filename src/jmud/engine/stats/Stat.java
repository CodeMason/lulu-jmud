package jmud.engine.stats;

import java.util.ArrayList;

/**
 * Stat is the object that represents a Character's statistic. Behavior of this
 * Stat is defined by a corresponding StatDef. Stat objects and StatDef objects
 * are correlated via the 'String name' field.
 * @author David Loman
 * @version 0.1
 */
public class Stat {
   private String name = "";
   private int max = -1;
   private int min = -1;
   private int current = -1;
   private final ArrayList<StatMod> mods = new ArrayList<StatMod>();

   // TODO why not have the Definition object reference attached at creation?
   private final AbstractStatDef defToUse = null;

   public AbstractStatDef getDefToUse() {
	return defToUse;
}

public Stat(final String name, final int max, final int min,
         final int current) {
      this.current = current;
      this.max = max;
      this.min = min;
      this.name = name;
   }

   public final int getCurrent() {
      return current;
   }

   public final int getMax() {
      return max;
   }

   public final int getMin() {
      return min;
   }

   public final ArrayList<StatMod> getMods() {
      return mods;
   }

   public final String getName() {
      return name;
   }

   public final void setCurrent(final int current) {
      this.current = current;
   }

   public final void setMax(final int max) {
      this.max = max;
   }

   public final void setMin(final int min) {
      this.min = min;
   }

}
