package jmud.engine.event;

import jmud.engine.object.JMudObject;

/**
 * An event to return upon the successful completion of a behavior
 */
public class SuccessEvent extends JMudEvent{
    private String message;

    /**
     * Constructs a Success Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public SuccessEvent(JMudObject source){
        this(source, null);
    }

    public SuccessEvent(JMudObject source, String message){
        super(source);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
