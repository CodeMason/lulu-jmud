package jmud.engine.behavior;

import jmud.engine.object.JMudObject;

import java.util.ArrayList;
import java.util.List;

public class BehaviorRegistrar {

	private static ArrayList<String> behaviors = new ArrayList<String>();

	public static boolean add(String s) {
		return behaviors.add(s);
	}

	public static boolean contains(String s) {
		return behaviors.contains(s);
	}

	public static boolean remove(String s) {
		return behaviors.remove(s);
	}

	

	
	
}
