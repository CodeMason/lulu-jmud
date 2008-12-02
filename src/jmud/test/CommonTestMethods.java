package jmud.test;

import java.util.Collection;

import jmud.engine.object.JMudObject;

public class CommonTestMethods {
	public static JMudObject buildTestJMudObjectTree() {
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

		root.childrenAdd(room);
		room.childrenAdd(pcSteve);
		room.childrenAdd(orc0);
		room.childrenAdd(orc1);
		room.childrenAdd(chair);
		room.childrenAdd(bag);
		room.childrenAdd(door);

		bag.childrenAdd(goldcoins);
		bag.childrenAdd(mapOfDungeon);

		return root;
	}

	public static void printTreeRecursor(final Collection<JMudObject> jmo) {
		for (JMudObject j : jmo) {
			CommonTestMethods.printTreeRecursor(j, 0);
		}
	}

	public static void printTreeRecursor(final JMudObject jmo) {
		CommonTestMethods.printTreeRecursor(jmo, 0);
	}

	public static void printTreeRecursor(final JMudObject jmo, final int lvl) {
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
		for (JMudObject j : jmo.childrenGetAll().values()) {
			CommonTestMethods.printTreeRecursor(j, lvl + 1);
		}
		return;
	}

	public static void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
