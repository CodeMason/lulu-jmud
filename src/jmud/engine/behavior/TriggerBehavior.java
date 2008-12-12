package jmud.engine.behavior;

import jmud.engine.event.JMudEventParticipantRole;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 *
 *
 * @author Dave Loman
 * @date December 4, 2008
 */

public class TriggerBehavior extends Behavior {

	private JMudEventParticipantRole triggerEventRole;
	private JMudObject triggerEventObject;
	private JMudEventParticipantRole sourceEventRoleForResponseEventTarget;
	private JMudObject responseObject;
	private JMudEventType responseEventType;

	public TriggerBehavior(JMudObject owner, JMudEventType triggerEventType, JMudEventParticipantRole triggerEventRole,
			JMudObject triggerEventObject, JMudEventParticipantRole sourceEventRoleForResponseEventTarget, JMudEventType responseEventType) {
		super(owner);
		this.eventTypesHandled.add(triggerEventType);
		this.triggerEventRole = triggerEventRole;
		this.triggerEventObject = triggerEventObject;
		this.sourceEventRoleForResponseEventTarget = sourceEventRoleForResponseEventTarget;
		this.responseEventType = responseEventType;
	}

	public TriggerBehavior(JMudObject owner, JMudEventType typeTrigger, JMudEventParticipantRole triggerEventRole,
			JMudObject triggerEventObject, JMudObject responseObject, JMudEventType responseEventType) {
		super(owner);
		this.eventTypesHandled.add(typeTrigger);
		this.triggerEventRole = triggerEventRole;
		this.triggerEventObject = triggerEventObject;
		this.responseObject = responseObject;
		this.responseEventType = responseEventType;
	}

	@Override
	protected boolean ccBehavior() {

		// check to see if the triggerEventRole matches the triggerEventObject
		JMudObject obj = null;

		// find out which JMudObject in the JMudEvent we are to check against.
		if (this.triggerEventRole == JMudEventParticipantRole.SOURCE) {
			obj = this.event.getSource();
		} else if (this.triggerEventRole == JMudEventParticipantRole.TARGET) {
			obj = this.event.getTarget();
		} else {
			// Now this *SHOULDNT* have happened.
			System.err.println("triggerEventRole is niether Source nor Target in TriggerBehavior!");
			return false;
		}

		// check vs triggerEventObject
		if (this.triggerEventObject.getUuid() != obj.getUuid()) {
			// Nope this isn't the object that will trip the trigger
			return false;
		}

		/*
		 * The correct object has issued the correct event... time to trigger!
		 */

		// find out which JMudObject we are supposed to send our new event to
		if (this.responseObject == null && this.sourceEventRoleForResponseEventTarget == null) {
			// We have no way of determining a target.... this bad!
			System.err.println("Object and Participant are null in TriggerBehavior!");
			return false;
		}
		JMudObject src = this.owner;
		JMudObject tgt = null;

		if (this.responseObject == null) {
			// If we supplied a Participant but not a JMudObject....

			if (this.sourceEventRoleForResponseEventTarget == JMudEventParticipantRole.SOURCE) {
				tgt = this.event.getSource();
			} else if (this.sourceEventRoleForResponseEventTarget == JMudEventParticipantRole.TARGET) {
				tgt = this.event.getTarget();
			} else {
				// Now this *SHOULDNT* have happened.
				System.err.println("sourceEventRoleForResponseEventTarget is niether Source nor Target in TriggerBehavior!");
				return false;
			}
		} else {
			// If we supplied a JMudObject but not a Participant...
			tgt = this.responseObject;
		}

		JMudEvent jme = new JMudEvent(this.responseEventType, src, tgt);
		jme.submitSelf();

		return true;
	}

	@Override
	protected boolean sourceBehavior() {
		return false;
	}

	@Override
	protected boolean targetBehavior() {
		return false;
	}

	@Override
	public Behavior clone() {

		if (this.responseObject == null) {
			return new TriggerBehavior(this.owner, this.eventTypesHandled.get(0), this.triggerEventRole, this.triggerEventObject,
					this.sourceEventRoleForResponseEventTarget, this.responseEventType);
		} else {
			return new TriggerBehavior(this.owner, this.eventTypesHandled.get(0), this.triggerEventRole, this.triggerEventObject,
					this.responseObject, this.responseEventType);
		}
	}

}