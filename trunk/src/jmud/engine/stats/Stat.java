/**
 * This file is part of Lulu's JMud.
 *
 *  Lulu's JMud is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Lulu's JMud is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Lulu's JMud.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmud.engine.stats;

import java.util.ArrayList;

/**
 * <code>Stat</code> is the object that represents a <code>Character's</code> statistic.
 * Behavior of this <code>Stat</code> is defined by a corresponding
 * <code>StatDef</code>. <code>Stat</code> objects and StatDef objects are
 * correlated via the '<code>String name</code>' field.
 * @author David Loman
 * @version 0.1
 */
public class Stat {
   private int current = -1;
   // TODO why not have the Definition object reference attached at creation?
   private final AbstractStatDef defToUse = null;
   private int max = -1;
   private int min = -1;
   private final ArrayList<StatMod> mods = new ArrayList<StatMod>();

   private String name = "";

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

   public AbstractStatDef getDefToUse() {
      return defToUse;
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
