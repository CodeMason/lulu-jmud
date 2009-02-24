package jmud.object;

import jmud.TestUtil;
import jmud.engine.object.JMudObject;
import jmud.object.templates.Generators;

public class PrintTreeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JMudObject world = Generators.generateSimpleWorld();

		TestUtil.printJMudObjectTree(world);
	}

}
