package jmud.engine.test;

import jmud.engine.behavior.GetBehavior;
import jmud.engine.event.JMudEventType;
import jmud.engine.event.JMudEvent;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;

public class EventBehaviorTest01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Initialize the JobManager with 1 worker
		JobManager.getInstance().init(1);
		
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

		
		//make a new event
		JMudEvent ge = new JMudEvent(JMudEventType.GetEvent, pcSteve, bag);
		System.out.println("GetEvent jobID is: " + ge.getJobID());
		
		//short sleep so we can test 'waking' a JobWorker
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Submit the event
		ge.submitSelf();
		
		//another sleep to ensure all events are processed
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	
		// Printout the tree.
		System.out.println("\n\nModified Tree");
		CommonTestParts.printTreeRecursor(root);

	}

}
