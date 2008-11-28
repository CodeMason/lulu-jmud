package jmud.engine.test;

import jmud.engine.behavior.GetBehavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;
import jmud.engine.object.test.JMudObjectTreeTest01;

public class EventBehaviorTest01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Initialize the JobManager with 1 worker
		JobManager.getInstance().init(2);
		
		//Build a simple JMudObjectTree
		JMudObject root = CommonTestParts.buildTestJMudObjectTree();
		JMudObject bag = root.childrenGet("room").childrenGet("bag");
		JMudObject pcSteve = root.childrenGet("room").childrenGet("pcSteve");
		
		
		//Establish object's behaviors
		GetBehavior bg = new GetBehavior();
		bag.addEventBehavior(bg);
		
		
		// Printout the tree.
		System.out.println("\n\nOriginal Tree");
		CommonTestParts.printTreeRecursor(root);

		
		//TODO left off here!
		//JMudEvent ge = new JMudEvent();
		
		
		
	}

}
