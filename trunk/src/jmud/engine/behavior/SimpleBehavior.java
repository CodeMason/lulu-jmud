package jmud.engine.behavior;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.SimpleEvent;
import jmud.engine.event.SuccessEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Simple representation of a Behavior. This is a Singleton
 * because I can't see any need for having multiple instances, although
 * I did make the behave method synchronized.
 *
 * Any object interested in handling the returned SuccessEvent would
 * register a Behavior for it (e.g. SendSuccessMessageToPlayer); any
 * object without a registered Behavior would just ignore it.
 */
public class SimpleBehavior extends Behavior{
    // ToDo: no idea how to genericize this
    private static final List VALID_EVENTS = Arrays.asList(SimpleEvent.class);
     /*
      * ********************************************
      * Singleton Implementation
      * ********************************************
      */

    /**
     * Protected constructor is sufficient to suppress unauthorized calls to the
     * constructor
     */
    protected SimpleBehavior(){
    }

    /**
     * BehaviorHolder is loaded on the first execution of
     * SimpleBehavior.getInstance() or the first access to
     * SimpleBehaviorHolder.INSTANCE, not before.
     */
    private static class SimpleBehaviorHolder{
        private static final SimpleBehavior INSTANCE = new SimpleBehavior();
    }

    public static SimpleBehavior getInstance(){
        return SimpleBehaviorHolder.INSTANCE;
    }


    /**
     * perform this Behavior's behavior and return the resultant
     * event.
     *
     * @param event the event the bahavior is responding too
     * @return the resulting EventObject
     */
    @Override
    public synchronized List<? extends JMudEvent> behave(JMudEvent event){
        return Arrays.asList(new SuccessEvent(event.getSource(), "Simple behavior succeeded"));
    }

    public List<Class> getValidEvents(){
        return VALID_EVENTS;
    }
}
