package jmud.engine.event;

import jmud.engine.object.JMudObject;

public class SimpleEvent extends JMudEvent{

    public SimpleEvent(JMudObject source){
        super(source);
    }
}
