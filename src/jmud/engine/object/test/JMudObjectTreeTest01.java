package jmud.engine.object.test;

import java.util.Collection;
import jmud.engine.object.JMudObject;

public class JMudObjectTreeTest01 {

	public static void main(String[] args) {
		JMudObject root = new JMudObject("root");
		JMudObject room = new JMudObject("room");
		JMudObject pcSteve = new JMudObject("pcSteve");
		JMudObject orc0 = new JMudObject("orc0");
		JMudObject orc1 = new JMudObject("orc1");
		JMudObject chair = new JMudObject("chair");
		JMudObject bag = new JMudObject("bag");
		JMudObject goldcoins = new JMudObject("goldcoins");
		JMudObject mapOfDungeon = new JMudObject("mapOfDungeon");
		JMudObject door = new JMudObject("door");

		root.addChild(room);
		room.addChild(pcSteve);
		room.addChild(orc0);
		room.addChild(orc1);
		room.addChild(chair);
		room.addChild(bag);
		room.addChild(door);

		bag.addChild(goldcoins);
		bag.addChild(mapOfDungeon);

		// Printout the tree.
		System.out.println("\n\nOriginal Tree");
		JMudObjectTreeTest01.printTreeRecursor(root);


		/*
		 *  Now I want to see JUST Chair's siblings:
		 */
		System.out.println("\n\nChair:");
		JMudObjectTreeTest01.printTreeRecursor(chair);

		// Printout the tree.
		System.out.println("\n\nChair's siblings");
		JMudObjectTreeTest01.printTreeRecursor(chair.getSiblings().values());


		
		/*
		 *  Re-arrange
		 */
		bag.changeParent(pcSteve);

		// Printout the tree.
		System.out.println("\n\nMoved Bag from the Room to pcSteve using JMudObject.changeParent(bag, pcSteve)");
		JMudObjectTreeTest01.printTreeRecursor(root);

		
		/*
		 *  Drop parent
		 */
		bag.changeParent(null);

		// Printout the tree.
		System.out.println("\n\nRemoved Bag from pcSteve using .changeParent(null)");
		JMudObjectTreeTest01.printTreeRecursor(root);

		/*
		 *  Reattach to Orc.0
		 */
		bag.changeParent(orc0);

		// Printout the tree.
		System.out.println("\n\nAttached Bag to ocr0 using .changeParent(orc0)");
		JMudObjectTreeTest01.printTreeRecursor(root);

		/*
		 * Orc.0 'drops' bag
		 */
		bag.changeParent(orc0.getParent());

		// Printout the tree.
		System.out.println("\n\nSimulated orc0 'drop' Bag using .changeParent(orc0.getParent())");
		JMudObjectTreeTest01.printTreeRecursor(root);

		
		
		
	}

	private static void printTreeRecursor(JMudObject jmo) {
		JMudObjectTreeTest01.printTreeRecursor(jmo, 0);
	}

	private static void printTreeRecursor(Collection<JMudObject> jmo) {
		for (JMudObject j : jmo) {
			JMudObjectTreeTest01.printTreeRecursor(j, 0);
		}
	}

	private static void printTreeRecursor(JMudObject jmo, int lvl) {
		// Declare output string
		String s = "";
		// Build tabs
		for (int i = 0; i < lvl; ++i) {
			s += "\t";
		}

		// attach the UUID:
		s += jmo.toString();

		// Print the string
		System.out.println(s);

		// recurse on all children
		for (JMudObject j : jmo.getAllChildren().values()) {
			JMudObjectTreeTest01.printTreeRecursor(j, lvl + 1);
		}
		return;
	}

}
