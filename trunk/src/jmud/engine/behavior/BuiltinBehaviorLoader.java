package jmud.engine.behavior;

public class BuiltinBehaviorLoader {

	public static void load() {
		
		BehaviorGenerator<GetBehavior> get = new BehaviorGenerator<GetBehavior>("GetBehavior");
		BehaviorRegistrar.getInstance().registerBehaviorGen(get);

		BehaviorGenerator<GotBehavior> got = new BehaviorGenerator<GotBehavior>("GotBehavior");
		BehaviorRegistrar.getInstance().registerBehaviorGen(got);

		//...
		
		
	}
	
}