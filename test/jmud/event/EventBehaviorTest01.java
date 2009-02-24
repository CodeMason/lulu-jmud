package jmud.event;

import jmud.TestUtil;
import jmud.engine.behavior.BehaviorManager;
import jmud.engine.behavior.BehaviorType;
import jmud.engine.behavior.BuiltinBehaviorLoader;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.JobManager;
import jmud.engine.job.definitions.RunEventJob;
import jmud.engine.object.JMudObject;
import jmud.engine.object.JMudObjectUtils;

public class EventBehaviorTest01 {

	public static void main(String[] args) {

		//Start a JobManager
		JobManager.getInstance().init(4);
		
		//Start the BehaviorManager
		BehaviorManager.getInstance();
		
		//Load the 'built-in' Behaviors
		BuiltinBehaviorLoader.load();
		
		/*
		 * Generate a simple world.
		 */
		JMudObject world = new JMudObject("MyWorld");
		
		JMudObject room = new JMudObject("MyRoom");
		JMudObjectUtils.changeParent(world, room);
		
		JMudObject pcSteve= new JMudObject("PCSteve");
		pcSteve.getEventBehaviorMap().addMapping(JMudEventType.Get, BehaviorType.Get);
		pcSteve.getEventBehaviorMap().addMapping(JMudEventType.Got, BehaviorType.Got);
		JMudObjectUtils.changeParent(room, pcSteve);

		JMudObject pcJoe = new JMudObject("PCJoe");
		pcJoe.getEventBehaviorMap().addMapping(JMudEventType.Get, BehaviorType.Get);
		pcJoe.getEventBehaviorMap().addMapping(JMudEventType.Got, BehaviorType.Got);
		JMudObjectUtils.changeParent(room, pcJoe);

		JMudObject sword = new JMudObject("sword");
		sword.getEventBehaviorMap().addMapping(JMudEventType.Get, BehaviorType.Get);
		sword.getEventBehaviorMap().addMapping(JMudEventType.Got, BehaviorType.Got);
		JMudObjectUtils.changeParent(room, sword);

		TestUtil.printJMudObjectTree(world);
		
		/*
		 * Start a getEvent
		 */
		
		JMudEvent jme = new JMudEvent(JMudEventType.Get, pcSteve, sword);
		RunEventJob rej = new RunEventJob(jme);
		rej.selfSubmit();
		
		TestUtil.pause(2500L);
		System.out.println("\n\n");
		TestUtil.printJMudObjectTree(world);
		
		
		JobManager.getInstance().stopAllWorkers();
	}
}
