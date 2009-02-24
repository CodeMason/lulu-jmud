package jmud.engine.behavior.definitions;

import jmud.engine.behavior.BehaviorRegistrar;
import jmud.engine.behavior.BehaviorType;
import jmud.engine.event.JMudEvent;
import jmud.engine.object.JMudObject;

/**
 * Base class for all Behaviors to extend from.  A behavior is a reusable object
 * that contains code to perform actions on an object based on a passed in EventType.
 */
public abstract class AbstractBehavior {

	protected BehaviorType beType;
	
	/*
	 * Constructors.
	 */
	public AbstractBehavior() {
	}

	/**
	 * perform this <code>Behavior's</code> behavior.
	 * 
	 * @return true if behavior completes successfully
	 */
	public boolean runBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme) {

		if (whoToRunThisBehaviorOn == jme.getTarget()) {
			return this.targetBehavior(whoToRunThisBehaviorOn, jme);

		} else if (whoToRunThisBehaviorOn == jme.getSource()) {
			return this.sourceBehavior(whoToRunThisBehaviorOn, jme);

		} else {
			return this.bystanderBehavior(whoToRunThisBehaviorOn, jme);
		}
	}

	/**
	 * Execute this code if this Behavior is the Event's target.
	 * 
	 * @return true if behavior completes successfully
	 */
	protected abstract boolean targetBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme);

	/**
	 * Execute this code if this Behavior is the Event's source.
	 * 
	 * @return true if behavior completes successfully
	 */
	protected abstract boolean sourceBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme);

	/**
	 * Execute this code if this Behavior's Owner has nothing to do with the
	 * Event.
	 * 
	 * @return true if behavior completes successfully
	 */
	protected abstract boolean bystanderBehavior(JMudObject whoToRunThisBehaviorOn, JMudEvent jme);

	public BehaviorType getBehaviorType() {
		return beType;
	}

	public void selfRegister() {
		BehaviorRegistrar.getInstance().register(this.getBehaviorType(), this);
	}
	
	
}