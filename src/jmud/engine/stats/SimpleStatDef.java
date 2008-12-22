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

/**
 * A simple implementation of an <code>AbstractStatDef</code> for use with the
 * core JMUD engine. ToDO: can events be kicked off by stat changes? If so we'll
 * want to look at using behaviors for stat events. (e.g. sending a message to
 * the user that their stat max has changed, or that they can now wield a
 * certain weapon or armor).
 * @author David Loman
 * @version 0.1
 */
public class SimpleStatDef extends AbstractStatDef {

   /**
    * Explicit constructor.
    * @param name
    * @param ns
    */
   public SimpleStatDef(final String name, final Namespace ns) {
      super(name, ns);
   }

   /**
    * Modify the <code>Stat</code> specified by <code>s</code> to a particular
    * value, <code>value</code>.
    * @see jmud.engine.stats.AbstractStatDef#modCurrent(jmud.engine.stats.Stat,
    *      int)
    * @param s
    *           the target <code>Stat</code>
    * @param value
    *           the new value
    * @return the new value
    */
   @Override
   public final int modCurrent(final Stat s, final int value) {
      s.setCurrent(s.getCurrent() + value);
      return s.getCurrent();
   }

   /**
    * Modify the maximum value of a <code>Stat</code> to a new value.
    * @param s
    *           the target <code>Stat</code>
    * @param value
    *           the delta to apply to the current maximum value of the target
    *           <code>Stat</code>
    * @return the new maximum value
    * @see jmud.engine.stats.AbstractStatDef#modMax(jmud.engine.stats.Stat, int)
    */
   @Override
   public final int modMax(final Stat s, final int value) {
      s.setMax(s.getMax() + value);
      return s.getMax();
   }

   /**
    * Modify the minimum value of a <code>Stat</code> to a new value.
    * @param s
    *           the target <code>Stat</code>
    * @param value
    *           the delta to apply to the current minimum value of the target
    *           <code>Stat</code>
    * @return the new minimum value
    * @see jmud.engine.stats.AbstractStatDef#modMin(jmud.engine.stats.Stat, int)
    */
   @Override
   public final int modMin(final Stat s, final int value) {
      s.setMin(s.getMin() + value);
      return s.getMin();
   }

}
