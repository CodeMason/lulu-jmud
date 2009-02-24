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
package jmud;

import jmud.engine.object.JMudObject;

import java.util.Collection;

public class TestUtil{

    public static JMudObject buildSimpleJMudObjectTree() {
		JMudObject root = new JMudObject("root");
		JMudObject room = new JMudObject("room");
		JMudObject pcSteve = new JMudObject("pcSteve");
		JMudObject orc0 = new JMudObject("orc0");
		JMudObject orc1 = new JMudObject("orc1");
		JMudObject chair = new JMudObject("chair");
		JMudObject bag = new JMudObject("bag");
		
        // QQQ CM: multiple coins as one object? Is this a design decision, or simply a name?
		// AAA DHL: Nah, just a place holder.  Dunno how we are going to implement large qtys of the same item...
		
        JMudObject goldcoins = new JMudObject("goldcoins");
		JMudObject mapOfDungeon = new JMudObject("mapOfDungeon");
		JMudObject door = new JMudObject("door");

		root.getJmoRelMap().addChild(room);
		room.getJmoRelMap().addChild(pcSteve);
		room.getJmoRelMap().addChild(orc0);
		room.getJmoRelMap().addChild(orc1);
		room.getJmoRelMap().addChild(chair);
		room.getJmoRelMap().addChild(bag);
		room.getJmoRelMap().addChild(door);

		bag.getJmoRelMap().addChild(goldcoins);
		bag.getJmoRelMap().addChild(mapOfDungeon);

		return root;
	}

    public static JMudObject buildObjectTree(int numberOfParentObjects){

        JMudObject root = new JMudObject("root");
        JMudObject room = new JMudObject("room");
        root.getJmoRelMap().addChild(room);

        for(int i = 0; i < numberOfParentObjects; i++){
//            room.getJmoRelMap().addChild(createOrcWithGearAndBehaviors());
        }

        return root;
    }


    public static void printJMudObjectTree(JMudObject root, String debugMessage){
        System.out.println("\n\n" + debugMessage);
        TestUtil.printJMudObjectTree(root);
        System.out.println("\n\n");
    }

    public static void printJMudObjectTree(final Collection<JMudObject> jmo) {
		for (JMudObject j : jmo) {
			TestUtil.printJMudObjectTree(j, 0);
		}
	}

	public static void printJMudObjectTree(final JMudObject jmo) {
		TestUtil.printJMudObjectTree(jmo, 0);
	}

	public static void printJMudObjectTree(final JMudObject jmo, final int lvl) {
		// Declare output string
		StringBuilder s = new StringBuilder();
		// Build tabs
		for (int i = 0; i < lvl; ++i) {
			s.append(" |-> ");
		}

		// attach the name:
		s.append(jmo.getDisplayedName());

		// Print the string
		System.out.println(s.toString());

		// recurse on all children
		for (JMudObject j : jmo.getJmoRelMap().getAllChildren()) {
			TestUtil.printJMudObjectTree(j, lvl + 1);
		}

        // CM: is this just a style thing?
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
