package jmud.engine.test;

import java.util.Collection;

import jmud.engine.object.JMudObject;
import jmud.engine.object.test.JMudObjectTreeTest01;

public class CommonTestParts {
	public static void printTreeRecursor(JMudObject jmo) {
		CommonTestParts.printTreeRecursor(jmo, 0);
	}

	public static void printTreeRecursor(Collection<JMudObject> jmo) {
		for (JMudObject j : jmo) {
			CommonTestParts.printTreeRecursor(j, 0);
		}
	}

	public static void printTreeRecursor(JMudObject jmo, int lvl) {
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
			CommonTestParts.printTreeRecursor(j, lvl + 1);
		}
		return;
	}


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
}
