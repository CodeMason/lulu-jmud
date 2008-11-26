package jmud.engine.job.definitions;

import jmud.engine.behavior.Behavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.object.JMudObject;

import java.util.List;

/**
 *
 * Runs a Behavior and creates any resulting Event_Jobs
 *
 * @created 26NOV08
 * @author Chris Maguire
 * @version 0.1
 */

public class Behavior_Job extends AbstractJob {

    private JMudEvent event;
    private Behavior behavior;
    private JMudObject target;

    public Behavior_Job(JMudEvent event, Behavior behavior, JMudObject target){
        this.event = event;
        this.behavior = behavior;
        this.target = target;
    }

    /**
     * Run the Behavior's behave and create an Event_Job for each resulting JMudEvent
     *
     * @return
     */
    @SuppressWarnings("ObjectAllocationInLoop")
    @Override
	public boolean doJob() {
        List<? extends JMudEvent> events = behavior.behave(event, target);
        if(events != null){
            for(JMudEvent event : events){
                new Event_Job(event, target).submitSelf();

            }
        }
        return false;
	}

}