package jmud.engine.event;

import jmud.engine.object.JMudObject;

/**
 * Sample event to make sure the inheritance is working and to
 * enable testing attaching events to behaviors.
 */
public class SampleEvent extends JMudEvent{

    public SampleEvent(JMudObject source){
        super(source);
    }
}
