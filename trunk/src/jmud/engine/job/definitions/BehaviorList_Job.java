package jmud.engine.job.definitions;

import jmud.engine.behavior.Behavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.object.JMudObject;

import java.util.List;

/**
 *
 * Takes a list of Behaviors and creates a job for each one
 *
 * @created 26NOV08
 * @author Chris Maguire
 * @version 0.1
 */

public class BehaviorList_Job extends AbstractJob {

    private JMudEvent event;
    private List<Behavior> behaviors;
    private JMudObject target;

    public BehaviorList_Job(JMudEvent event, List<Behavior> behaviors, JMudObject target){
        this.event = event;
        this.behaviors = behaviors;
        this.target = target;
    }

    /**
     * Spawn a job for each behavior in the list
     * @return
     */
    @SuppressWarnings({"ObjectAllocationInLoop"})
    @Override
	public boolean doJob() {
        for(Behavior behavior : behaviors){
            new Behavior_Job(event, behavior, target).submitSelf();
        }
        return false;
	}

}