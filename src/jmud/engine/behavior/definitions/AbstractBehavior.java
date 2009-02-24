package jmud.engine.behavior.definitions;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Following the command design pattern [GoF], a <code>Behavior</code> is a
 * discrete portion of logic that can be associated with a
 * <code>JMudObject</code> and called in response to a <code>JMudEvent</code>.
 * 
 * <code>Behaviors</code> should check that they are handling the right event.
 */
public abstract class AbstractBehavior {

	// These should be defined in the Subclasses, they are not dynamic
	protected ArrayList<JMudEventType> eventTypesHandled;
	protected JMudObject owner;
	protected JMudEvent event;

	/*
	 * Constructors.
	 */
	public AbstractBehavior(JMudObject owner) {
		this.owner = owner;
		this.eventTypesHandled = new ArrayList<JMudEventType>();
	}

	/*
	 * Getters n Setters
	 */

	public JMudObject getOwner() {
		return owner;
	}

	public void setOwner(JMudObject owner) {
		this.owner = owner;
	}

	public JMudEvent getEvent() {
		return this.event;
	}

	public void setEvent(JMudEvent jme) {
		this.event = jme;
	}

	public final List<JMudEventType> getEventTypesHandled() {
		return eventTypesHandled;
	}

	/**
	 * perform this <code>Behavior's</code> behavior.
	 * 
	 * @return true if behavior completes successfully
	 */
	public boolean runBehavior() {

		if (canHandleEvent(this.event) == false) {
			return false;
		}

		if (this.owner == this.event.getTarget()) {
			return this.targetBehavior();

		} else if (this.owner == this.event.getSource()) {
			return this.sourceBehavior();

		} else {
			return this.bystanderBehavior();
		}
	}

	public boolean canHandleEvent(JMudEvent event) {
		return (event != null) && (this.eventTypesHandled.contains(event.getEventType()));
	}

	/**
	 * Execute this code if this Behavior is the Event's target.
	 * 
	 * @return true if behavior completes successfully
	 */
	protected boolean targetBehavior() {

		return true;
	}

	/**
	 * Execute this code if this Behavior is the Event's source.
	 * 
	 * @return true if behavior completes successfully
	 */
	protected boolean sourceBehavior() {
		return true;
	}

	/**
	 * Execute this code if this Behavior's Owner has nothing to do with the
	 * Event.
	 * 
	 * @return true if behavior completes successfully
	 */
	protected boolean bystanderBehavior() {
		return true;
	}

}
