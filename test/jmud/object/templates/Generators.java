package jmud.object.templates;

import jmud.engine.object.JMudObject;

public class Generators {

	public static JMudObject generateSimpleWorld() {
		JMudObject world = new JMudObject("World");

		// Make 4 rooms with random stuff in it.
		Generators.generateRooms(world, 4, 4);

		for (JMudObject room : world.getJmoRelMap().getAllChildren()) {
			// Add 1-4 orcs
			Generators.addOrcs(room, (int) (Math.random() * 3) + 1);
		}

		return world;
	}

	public static void generateRooms(JMudObject world, int numOfRoomsToGenerate, int items) {
		JMudObject room;
		for (int i = 0; i < numOfRoomsToGenerate; ++i) {
			room = new JMudObject();
			room.setDisplayedName("Room #" + room.getUUID().toString().substring(1, 6));
			world.getJmoRelMap().addChild(room);

			JMudObject thing;

			if (items > 2) {
				thing = new JMudObject("A Golden Ring");
				room.getJmoRelMap().addChild(thing);
			}
			if (items > 1) {
				thing = new JMudObject("A Bent Piece of Metal");
				room.getJmoRelMap().addChild(thing);
			}
			if (items > 0) {
				thing = new JMudObject("A Chair");
				room.getJmoRelMap().addChild(thing);
			}

		}
	}

	public static void addOrcs(JMudObject room, int numOfOrcs) {
		JMudObject orc;
		for (int i = 0; i < numOfOrcs; ++i) {
			orc = new JMudObject();
			orc.setDisplayedName("Orc #" + orc.getUUID().toString().substring(1, 6));

			orc.getEventBehaviorMap();

			JMudObject weap;
			if ((Math.random() * 100) > 50) {
				weap = new JMudObject("Axe");
			} else {
				weap = new JMudObject("Sword");
			}

			orc.getJmoRelMap().addChild(weap);

			room.getJmoRelMap().addChild(orc);
		}
	}

}
