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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class StatMap {
   private final HashMap<String, Stat> stats = new HashMap<String, Stat>();

   public final void clear() {
      stats.clear();
   }

   @Override
   public final Object clone() {
      return stats.clone();
   }

   public final boolean containsKey(final Object arg0) {
      return stats.containsKey(arg0);
   }

   public final boolean containsValue(final Object arg0) {
      return stats.containsValue(arg0);
   }

   public final Set<Entry<String, Stat>> entrySet() {
      return stats.entrySet();
   }

   @Override
   public final boolean equals(final Object arg0) {
      return stats.equals(arg0);
   }

   public final Stat get(final Object arg0) {
      return stats.get(arg0);
   }

   @Override
   public final int hashCode() {
      return stats.hashCode();
   }

   public final boolean isEmpty() {
      return stats.isEmpty();
   }

   public final Set<String> keySet() {
      return stats.keySet();
   }

   public final Stat put(final String arg0, final Stat arg1) {
      return stats.put(arg0, arg1);
   }

   public final void putAll(final Map<? extends String, ? extends Stat> arg0) {
      stats.putAll(arg0);
   }

   public final Stat remove(final Object arg0) {
      return stats.remove(arg0);
   }

   public final int size() {
      return stats.size();
   }

   @Override
   public final String toString() {
      return stats.toString();
   }

   public final Collection<Stat> values() {
      return stats.values();
   }

}
