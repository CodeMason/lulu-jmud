package jmud.object;

import jmud.engine.object.JMudObject;
import jmud.TestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JMudObjectTreeTest01 {
   private JMudObject bag;
   private JMudObject pcSteve;
   private JMudObject orc0;

   private void getTestJMudObjects(final JMudObject root) {
      bag = root.getChildObject("room").getChildObject("bag");
      pcSteve = root.getChildObject("room").getChildObject("pcSteve");
      orc0 = root.getChildObject("room").getChildObject("orc0");
   }

   @Before
   public final void setup() {
      getTestJMudObjects(TestUtil.buildSimpleJMudObjectTree());
   }

   @Test
   public final void testAddJMudObjectParent() {
      bag.changeParent(null);
      bag.changeParent(orc0);
      Assert.assertEquals(
            "JMudObject \"bag\" was not transferred to JMudObject \"orc0\"",
            bag.getParentObject(), orc0);
   }

   @Test
   public final void testChangeJMudObjectParent() {
      bag.changeParent(pcSteve);
      Assert.assertEquals(
            "JMudObject \"bag\" was not transferred to JMudObject \"pcSteve\"",
            bag.getParentObject(), pcSteve);
   }

   @Test
   public final void testDropJMudObjectToParent() {
      bag.changeParent(pcSteve);
      bag.changeParent(pcSteve.getParentObject());
      Assert
            .assertEquals(
                  "JMudObject \"bag\" was not \"dropped\" to JMudObject \"room\" from JMudObject \"pcSteve\"",
                  bag.getParentObject(), pcSteve.getParentObject());
   }

   @Test
   public final void testRemoveJMudObjectParent() {
      bag.orphan();
      Assert.assertNull(
            "JMudObject \"bag\" was not removed from JMudObject \"pcSteve\"",
            bag.getParentObject());
   }

}
