package jmud.engine.event.deprecated;

import jmud.engine.event.JMudEvent;
import jmud.engine.object.JMudObject;

/**
 * Sample event to make sure the inheritance is working and to
 * enable testing attaching events to behaviors.
 */
public class SampleEvent extends JMudEvent{

    public SampleEvent(JMudObject source){
        super(null, source, null);   }
}
