package jmud.engine.behavior;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Following the command design pattern [GoF], a <code>Behavior</code> is a
 * discrete portion of logic that can be associated with a
 * <code>JMudObject</code> and called in response to a <code>JMudEvent</code>.
 * 
 * <code>Behaviors</code> should check that they are handling the right event.
 */
public class BaseBehavior {
	
	protected List<JMudEventType> eventTypesHandled;
	protected JMudObject owner;
	protected String name;
	protected JMudEvent event;

	/*
	 * Constructors.
	 */
	public BaseBehavior() {
		this("", null);
	}

	public BaseBehavior(String name) {
		this(name + "", null);
	}

	public BaseBehavior(JMudObject owner) {
		this("", owner);
	}

	public BaseBehavior(String name, JMudObject owner) {
		this.name = name;
		this.owner = owner;
		this.eventTypesHandled = Collections.synchronizedList(new ArrayList<JMudEventType>());
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
