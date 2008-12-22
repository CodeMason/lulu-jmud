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

import jmud.engine.core.Namespace;

import java.util.ArrayList;

/**
 * Provides mandatory base implementation for all 'StatDef's'.
 * @author David Loman
 * @version 0.1
 */
public abstract class AbstractStatDef {
   private String name = "";
   private final ArrayList<Namespace> namespaces = new ArrayList<Namespace>();

   public AbstractStatDef(final String name, final Namespace ns) {
      super();
      this.name = name;
      this.namespaces.add(ns);
   }

   public final boolean add(final Namespace ns) {
      return namespaces.add(ns);
   }

   public final int decrCurrent(final Stat s) {
      return this.modCurrent(s, -1);
   }

   public final int decrMax(final Stat s) {
      return this.modMax(s, -1);
   }

   public final int decrMin(final Stat s) {
      return this.modMin(s, -1);
   }

   public final String getName() {
      return name;
   }

   /*
    * Value Modifiers
    */

   public final ArrayList<Namespace> getNamespaces() {
      return namespaces;
   }

   public final int incrCurrent(final Stat s) {
      return this.modCurrent(s, 1);
   }

   public final int incrMax(final Stat s) {
      return this.modMax(s, 1);
   }

   public final int incrMin(final Stat s) {
      return this.modMin(s, 1);
   }

   public abstract int modCurrent(Stat s, int value);

   public abstract int modMax(Stat s, int value);

   public abstract int modMin(Stat s, int value);

   public final boolean remove(final Object o) {
      return namespaces.remove(o);
   }

   public final void selfRegister() {
      StatDefRegistrar.getInstance().addStatDef(this);
   }

}
