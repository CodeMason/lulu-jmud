package jmud.engine.event.deprecated;

import jmud.engine.event.EventType;
import jmud.engine.event.JMudEvent;
import jmud.engine.object.JMudObject;

/**
 * An event to fire upon the failure to successfully (in the context of Player intent)
 * perform a behavior; not to be confused with failure to process a Behavior.
 */
public class FailureEvent extends JMudEvent{

    private String message;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public FailureEvent(JMudObject source){
        this(source, null);
    }

    public FailureEvent(JMudObject source, String message){
        super(EventType.FailedEvent, source, null);
        this.message = message;
    }

    
    
    
    
    
    
    
    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
