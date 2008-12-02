package jmud.test;

import jmud.engine.behavior.DisplayTextStdErrBehavior;
import jmud.engine.behavior.DisplayTextStdOutBehavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventRegistrar;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.JobManager;
import jmud.engine.object.JMudObject;

public class DisplayTextEventBehaviorTest01 {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		// Initialize the JobManager with 1 worker
		JobManager.getInstance().init(1);

		// Initialize the EventRegistrar
		JMudEventRegistrar.getInstance().init();

		// Build a simple JMudObjectTree
		JMudObject root = CommonTestMethods.buildTestJMudObjectTree();

		// get handles on some of the objects

		JMudObject pcSteve = root.childrenGet("room").childrenGet("pcSteve");
		JMudObject orc0 = root.childrenGet("room").childrenGet("orc0");
		JMudObject orc1 = root.childrenGet("room").childrenGet("orc1");

		// Establish behaviors
		orc0.addEventBehavior(new DisplayTextStdOutBehavior(orc0));
		orc1.addEventBehavior(new DisplayTextStdErrBehavior(orc1));

		// make a new event
		JMudEvent dtsoe = new JMudEvent(JMudEventType.DisplayTextStdOutEvent, pcSteve, orc0);
		dtsoe.getDataMap().put("displayText", "This is a test of the StdOut system..... BEEEEEEEP");
		System.out.println("DisplayTextStdOutEvent eventID is: " + dtsoe.getID());

		// Submit the event
		dtsoe.submitSelf();

		// make a new event
		dtsoe = new JMudEvent(JMudEventType.DisplayTextStdErrEvent, pcSteve, orc1);
		dtsoe.getDataMap().put("displayText", "This is a test of the StdErr system..... BEEEEEEEP");
		System.out.println("DisplayTextStdOutEvent eventID is: " + dtsoe.getID());

		// Submit the event
		dtsoe.submitSelf();

		// another sleep to ensure all events are processed
		CommonTestMethods.pause(2500);

		JobManager.getInstance().stopAllWorkers();

	}

}
