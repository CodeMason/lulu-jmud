package jmud.engine.item;

import jmud.engine.core.Targetable;

public class SimpleItemContainerDef extends AbstractItemContainerDef {

   public SimpleItemContainerDef(final String name) {
      super(name);
   }

   @Override
   protected final boolean addCheck(final Targetable targetAdding,
         final ItemContainer ic, final Item i) {
      return true;
   }

   @Override
   protected final boolean getCheck(final Targetable targetGetting,
         final Item it) {
      return true;
   }

   @Override
   protected final boolean putCheck(final Targetable targetPutting,
         final Item it) {
      return true;
   }

   @Override
   protected final boolean remCheck(final Targetable targetRemoving,
         final ItemContainer ic, final Item i) {
      return true;
   }

   @Override
   public final boolean use(final Targetable targetUsing, final Item it) {
      return true;
   }
}
