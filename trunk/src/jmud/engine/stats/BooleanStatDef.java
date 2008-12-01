package jmud.engine.stats;

import jmud.engine.core.Namespace;

/**
 * A simple implementation of an AbstractStatDef for use with the core JMUD
 * engine.
 * @author David Loman
 * @version 0.1
 */
public class BooleanStatDef extends AbstractStatDef {

   public BooleanStatDef(final String name, final Namespace ns) {
      super(name, ns);
   }

   /*
    * Value Modifiers
    */

   @Override
   public final int modCurrent(final Stat s, final int value) {
      int tval = s.getCurrent() + value;

      if (tval <= 0) {
         tval = 0;
      } else {
         tval = 1;
      }

      s.setCurrent(tval);
      return s.getCurrent();
   }

   @Override
   public final int modMax(final Stat s, final int value) {
      s.setMax(1);
      System.err.println("Attemped to change MAX of a BooleanStat");
      return s.getMax();
   }

   @Override
   public final int modMin(final Stat s, final int value) {
      s.setMin(0);
      System.err.println("Attemped to change MIN of a BooleanStat");
      return s.getMin();
   }

}
