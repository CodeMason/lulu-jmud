package jmud.engine.object.test;

import jmud.engine.object.JMudObject;
import jmud.engine.test.CommonTestParts;

public class JMudObjectTreeTest01 {

   public static void main(final String[] args) {
      JMudObject root = CommonTestParts.buildTestJMudObjectTree();

      // yes this seems a bit bass-ackwards, but is shows the ability
      // to look things up by name ;)
      JMudObject chair = root.childrenGet("room").childrenGet("chair");
      JMudObject bag = root.childrenGet("room").childrenGet("bag");
      JMudObject pcSteve = root.childrenGet("room").childrenGet("pcSteve");
      JMudObject orc0 = root.childrenGet("room").childrenGet("orc0");

      // Printout the tree.
      System.out.println("\n\nOriginal Tree");
      CommonTestParts.printTreeRecursor(root);

      /*
       * Now I want to see JUST Chair's siblings:
       */
      System.out.println("\n\nChair:");
      CommonTestParts.printTreeRecursor(chair);

      // Printout the tree.
      System.out.println("\n\nChair's siblings");
      CommonTestParts.printTreeRecursor(chair.getSiblings().values());

      /*
       * Re-arrange
       */
      bag.changeParent(pcSteve);

      // Printout the tree.
      System.out
            .println("\n\nMoved Bag from the Room to pcSteve using JMudObject.changeParent(bag, pcSteve)");
      CommonTestParts.printTreeRecursor(root);

      /*
       * Drop parent
       */
      bag.changeParent(null);

      // Printout the tree.
      System.out
            .println("\n\nRemoved Bag from pcSteve using .changeParent(null)");
      CommonTestParts.printTreeRecursor(root);

      /*
       * Reattach to Orc.0
       */
      bag.changeParent(orc0);

      // Printout the tree.
      System.out.println("\n\nAttached Bag to ocr0 using .changeParent(orc0)");
      CommonTestParts.printTreeRecursor(root);

      /*
       * Orc.0 'drops' bag
       */
      bag.changeParent(orc0.getParent());

      // Printout the tree.
      System.out
            .println("\n\nSimulated orc0 'drop' Bag using .changeParent(orc0.getParent())");
      CommonTestParts.printTreeRecursor(root);

   }

}
