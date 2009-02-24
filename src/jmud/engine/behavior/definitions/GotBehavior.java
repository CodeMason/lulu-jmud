package jmud.engine.behavior.definitions;

import jmud.engine.behavior.BehaviorType;
import jmud.engine.event.JMudEvent;
import jmud.engine.object.JMudObject;

/**
 * 
 * @author david.h.loman
 */
public class GotBehavior extends AbstractBehavior {
	
	public GotBehavior() {
		this.beType = BehaviorType.Got;
	}

	/**
	 * Anything the object that did the 'get' does
	 * 
	 * @return
	 */
	@Override
	public final boolean targetBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		// What the target sees

		String txt = "You get the " + jme.getSource().getDisplayedName() + ".";
		jme.getTarget().sendTextToObject(txt);

		return true;
	}

	/**
	 * Anything a non-participant does
	 * 
	 * @return
	 */
	@Override
	protected boolean bystanderBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		// What anyone else sees.
		String txt = jme.getTarget().getDisplayedName() + " gets the " + jme.getSource().getDisplayedName() + ".";
		whoToRunThisBehaviorOn.sendTextToObject(txt);

		return true;
	}

	/**
	 * Anything the "gotten" object does
	 * 
	 * @return
	 */
	@Override
	protected boolean sourceBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {
		// What the source sees.

		String txt = jme.getTarget().getDisplayedName() + " picks YOU up!!";
		jme.getSource().sendTextToObject(txt);

		return true;
	}
}
