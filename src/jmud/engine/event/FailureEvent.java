package jmud.engine.event;

import jmud.engine.object.JMudObject;

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
