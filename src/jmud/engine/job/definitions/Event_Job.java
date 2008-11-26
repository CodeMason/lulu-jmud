package jmud.engine.job.definitions;

import jmud.engine.behavior.Behavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.object.JMudObject;

import java.util.List;

/**
 *
 * Takes a JMudEvent and creates Behavior Jobs for it
 *
 * ToDo: this job would need to publish this event to any subscribers
 *
 * @created 26NOV08
 * @author Chris Maguire
 * @version 0.1
 */

public class Event_Job extends AbstractJob {

    private JMudEvent event;
    private JMudObject target;

    public Event_Job(JMudEvent event, JMudObject target){
        this.event = event;
        this.target = target;
    }

    /**
     * Find the behaviors that match this event from the target object
     *
     * ToDo How do we know how to fill in the information that a Behavior will need?
     *      Should we always pass in the event and target?
     *
     * @return dunno, what do jobs return?
     */
    @SuppressWarnings({"ObjectAllocationInLoop"})
    @Override
	public boolean doJob() {
        List<? extends Behavior> behaviors = target.getBehaviors(event);
        for(Behavior behavior : behaviors){
            new Behavior_Job(event, behavior, target).submitSelf();
        }
        return false;
	}

}