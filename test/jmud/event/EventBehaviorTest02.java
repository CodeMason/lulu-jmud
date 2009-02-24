package jmud.event;

import jmud.TestUtil;
import jmud.engine.behavior.BehaviorManager;
import jmud.engine.behavior.BehaviorType;
import jmud.engine.behavior.BuiltinBehaviorLoader;
import jmud.engine.event.AffectRange;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.JobManager;
import jmud.engine.job.definitions.RunEventJob;
import jmud.engine.object.JMudObject;
import jmud.engine.object.JMudObjectUtils;

public class EventBehaviorTest02 {

	public static void main(String[] args) {

		// Start a JobManager
		JobManager.getInstance().init(4);

		// Start the BehaviorManager
		BehaviorManager.getInstance();

		// Load the 'built-in' Behaviors
		BuiltinBehaviorLoader.load();

		/*
		 * Generate a BIGGER simple world.
		 */
		JMudObject world = new JMudObject("MyWorld");

		JMudObject room01 = new JMudObject("MyRoom01");
		JMudObjectUtils.changeParent(world, room01);

		JMudObject pcSteve = new JMudObject("PCSteve");
		pcSteve.getEventBehaviorMap().addMapping(JMudEventType.Get, BehaviorType.Get);
		pcSteve.getEventBehaviorMap().addMapping(JMudEventType.Got, BehaviorType.Got);
		JMudObjectUtils.changeParent(room01, pcSteve);

		JMudObject pcJoe = new JMudObject("PCJoe");
		pcJoe.getEventBehaviorMap().addMapping(JMudEventType.Get, BehaviorType.Get);
		pcJoe.getEventBehaviorMap().addMapping(JMudEventType.Got, BehaviorType.Got);
		JMudObjectUtils.changeParent(room01, pcJoe);

		JMudObject sword = new JMudObject("sword");
		sword.getEventBehaviorMap().addMapping(JMudEventType.Get, BehaviorType.Get);
		sword.getEventBehaviorMap().addMapping(JMudEventType.Got, BehaviorType.Got);
		JMudObjectUtils.changeParent(room01, sword);

		JMudObject room02 = new JMudObject("MyRoom02");
		JMudObjectUtils.changeParent(world, room02);

		JMudObject pcKaiser = new JMudObject("PCKaiser");
		pcKaiser.getEventBehaviorMap().addMapping(JMudEventType.Get, BehaviorType.Get);
		pcKaiser.getEventBehaviorMap().addMapping(JMudEventType.Got, BehaviorType.Got);
		JMudObjectUtils.changeParent(room02, pcKaiser);

		TestUtil.printJMudObjectTree(world);

		/*
		 * Start a getEvent with a AffectRange that goes one layer higher in
		 * Parent Heirarchy
		 */

		JMudEvent jme = new JMudEvent(JMudEventType.Get, pcSteve, sword, new AffectRange(2, 1));
		RunEventJob rej = new RunEventJob(jme);
		rej.selfSubmit();

		TestUtil.pause(500L);
		System.out.println("\n\n");
		TestUtil.printJMudObjectTree(world);
		TestUtil.pause(500L);

		JobManager.getInstance().stopAllWorkers();
	}
}
