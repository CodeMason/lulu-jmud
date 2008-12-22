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
