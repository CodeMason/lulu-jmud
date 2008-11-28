package jmud.engine.object.test;

import java.util.UUID;

import jmud.engine.object.JMudObject;

public class JMudObjectTreeTest01 {

	public static void main(String[] args) {
		JMudObject root = new JMudObject(UUID.randomUUID());
		JMudObject room = new JMudObject(UUID.randomUUID());
		JMudObject pcSteve = new JMudObject(UUID.randomUUID());
		JMudObject orc0 = new JMudObject(UUID.randomUUID());
		JMudObject orc1 = new JMudObject(UUID.randomUUID());
		JMudObject chair = new JMudObject(UUID.randomUUID());
		JMudObject bag = new JMudObject(UUID.randomUUID());
		JMudObject goldcoins = new JMudObject(UUID.randomUUID());
		JMudObject mapOfDungeon = new JMudObject(UUID.randomUUID());
		JMudObject door = new JMudObject(UUID.randomUUID());

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
		JMudObjectTreeTest01.printTreeRecursor(root);

		// re-arrange
		room.remChild(bag);
		pcSteve.addChild(bag);

		// Printout the tree.
		JMudObjectTreeTest01.printTreeRecursor(root);

	}

	private static void printTreeRecursor(JMudObject jmo) {
		System.out.println("\n\n");
		JMudObjectTreeTest01.printTreeRecursor(jmo, 0);
	}

	private static void printTreeRecursor(JMudObject jmo, int lvl) {
		// Declare output string
		String s = "";
		// Build tabs
		for (int i = 0; i < lvl; ++i) {
			s += "\t";
		}

		// attach the UUID:
		s += jmo.getUUID().toString();

		// Print the string
		System.out.println(s);

		// recurse on all children
		for (JMudObject j : jmo.getAllChildren().values()) {
			JMudObjectTreeTest01.printTreeRecursor(j, lvl + 1);
		}
		return;
	}

}
