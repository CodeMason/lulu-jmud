package jmud.engine.behavior;

import jmud.engine.event.JMudEventParticipantRole;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A behavior to fire off an attack event at the source JMudObject of some other event
 *
 * All you have to specify is the EventType, the parameters (if any) and whether you want the
 * new event fired at the source event's target JMudObject or source JMudObject.
 *
 * ToDo CM: I'm not sure that subclassing TriggerEventBehavior is versatile enough
 *          It would be nice to dynamically specify the triggered event
 *
 * @author Chris Maguire
 * @date December 2, 2008
 */
public class AttackSourceTriggerEventBehavior extends TriggerEventBehavior {

	public AttackSourceTriggerEventBehavior(JMudObject owner) {
		super(owner);
	}


    /**
     * Return the event type of the event that should be fired at the source, from the owner of this behavior
     * @return event type of event to fire
     */
    public JMudEventType getEventType(){
        return JMudEventType.Attack;
    }

    /**
     * What parameters are required for an attack
     *
     * ToDo CM: need some code to ensure that events created with an event type have the proper paramters
     *
     * @return a map of event parameters
     */
    public Map<String, Object> getParameters(){
        return new HashMap<String, Object>();
    }

    /**
     * Return what to use as the target of the triggered event, the source event's source JMudObject or target JMudObject
     * @return an enum value specifying to use the source Event's target JMudObject or source JMudObject as the triggered Event's target
     */
    public JMudEventParticipantRole getTargetSource(){
        return JMudEventParticipantRole.SOURCE;
    }

    /**
	 * @see jmud.engine.behavior.Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
	@Override
	public final Behavior clone() {
		synchronized (System.out) {
			System.out.println("AttackSourceTriggerEventBehavior.clone()");
		}

		return new AttackSourceTriggerEventBehavior(this.owner);
	}

}