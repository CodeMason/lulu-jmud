package jmud.engine.behavior;

import jmud.engine.event.JMudEventParticipantRole;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

import java.util.Map;

/**
 * Relax, I'm just trying some stuff out. I want an event that can be used to "Chain" events together:
 *
 * if A does something to B, then C does something to A
 * (e.g. Steve presses the button and an alarm sounds. Or an alarm sounds and a door opens.)
 *
 * The subscriptions can be set up so that events can trigger events which can trigger still more events and so on.
 *
 * Abstract class to fire off events in response to events
 *
 * @author Chris Maguire
 * @date December 2, 2008
 */
public abstract class TriggerEventBehavior extends Behavior {

	public TriggerEventBehavior(JMudObject owner) {
		super(owner);

        // what events does this behavior respond to? (e.g. certain behaviors may expect certain data)
        this.eventTypesHandled.add(JMudEventType.Trigger);
	}

    /**
     * Run the behavior
     *
     * @return true if behavior completes successfully
     */
    protected boolean behave(){
        // get the event to trigger
        JMudEvent eventToTrigger = new JMudEvent(this.getEventType(), this.getOwner(), this.getEvent().getSource());

        eventToTrigger.getDataMap().putAll(this.getParameters());

        System.out.println("Triggering event: " + eventToTrigger.toString());

        // Submit the event
        eventToTrigger.submitSelf();

        return true;
    }

    /**
	 * @see Behavior#behave()
	 * @return true
	 */
	@Override
	public final boolean targetBehavior() {
		return true;
	}

	@Override
	protected boolean ccBehavior() {
		return true;
	}

	@Override
	protected boolean sourceBehavior() {
		return this.ccBehavior();
	}

    /**
     * Return the event type of the event that should be fired at the source, from the owner of this behavior
     * @return event type of event to fire
     */
    abstract JMudEventType getEventType();

    /**
     * Get the parameters required to create the event to fire
     * @return a map of event parameters
     */
    abstract Map<String, Object> getParameters();

    /**
     * Return what to use as the target of the triggered event, the source event's source JMudObject or target JMudObject
     * @return an enum value specifying to use the source Event's target JMudObject or source JMudObject as the triggered Event's target
     */
    abstract JMudEventParticipantRole getTargetSource();

    /**
	 * @see Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
    /*
	@Override
	public final Behavior clone() {
		synchronized (System.out) {
			System.out.println("GetBehavior.clone()");
		}

		return new TriggerEventBehavior(this.owner);
	}*/



}