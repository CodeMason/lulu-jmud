package jmud.engine.behavior;

import jmud.engine.behavior.definitions.AbstractBehavior;
import jmud.engine.behavior.definitions.GetBehavior;
import jmud.engine.behavior.definitions.GotBehavior;

public class BuiltinBehaviorLoader {

	public static void load() {

		AbstractBehavior ab;
		
		ab = new GetBehavior();
		ab.selfRegister();
		
		ab = new GotBehavior();
		ab.selfRegister();
		
		// ...

	}

}
