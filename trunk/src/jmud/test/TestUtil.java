package jmud.test;

import jmud.engine.object.JMudObject;
import jmud.engine.behavior.*;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;

public class TestUtil{
    private static final List<String> STANDARD_GEAR_NAMES = Arrays.asList("Sword", "Shield", "Helmet");

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

		root.addChildObject(room);
		room.addChildObject(pcSteve);
		room.addChildObject(orc0);
		room.addChildObject(orc1);
		room.addChildObject(chair);
		room.addChildObject(bag);
		room.addChildObject(door);

		bag.addChildObject(goldcoins);
		bag.addChildObject(mapOfDungeon);

		return root;
	}

    public static JMudObject buildObjectTree(int numberOfParentObjects){

        JMudObject root = new JMudObject("root");
        JMudObject room = new JMudObject("room");
        root.addChildObject(room);

        for(int i = 0; i < numberOfParentObjects; i++){
            room.addChildObject(createOrcWithGearAndBehaviors());
        }

        return root;
    }

    private static JMudObject createOrcWithGearAndBehaviors(){
        JMudObject orc = new JMudObject("Orc");
        addBehaviorsToObject(orc, Arrays.asList((Class) GetBehavior.class, GotBehavior.class, AttackBehavior.class, AttackedBehavior.class));
        addGearObjectsWithBehaviors(orc, STANDARD_GEAR_NAMES);
        return orc;
    }

    private static void addGearObjectsWithBehaviors(JMudObject parentObject, List<String> gearNames){
        for(String gearName : gearNames){
            parentObject.addChildObject(createGearObjectWithBehaviors(gearName));
        }
    }

    private static JMudObject createGearObjectWithBehaviors(String childObjectName){
        JMudObject newObject = new JMudObject(childObjectName);
        addBehaviorsToObject(newObject, Arrays.asList((Class) GetBehavior.class, GotBehavior.class));
        return newObject;
    }

    private static void addBehaviorsToObject(JMudObject object, List<Class> behaviorClassesToAdd){
        for(Behavior behavior : BehaviorFactory.createBehaviors(behaviorClassesToAdd, object)){
            object.registerBehaviorForEventTypesHandled(behavior);
        }
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
			s.append("\t");
		}

		// attach the UUID:
		s.append(jmo.toString());

		// Print the string
		System.out.println(s.toString());

		// recurse on all children
		for (JMudObject j : jmo.getChildObjects().values()) {
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
