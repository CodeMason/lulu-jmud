package jmud.engine.event;

import jmud.engine.object.JMudObject;

public class JMudEvent{

    protected transient JMudObject source;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public JMudEvent(JMudObject source){
        this.source = source;
    }

    /**
     * The object on which the Event initially occurred.
     *
     * @return The object on which the Event initially occurred.
     */
    public JMudObject getSource(){
        return source;
    }
}
