package jmud.engine.event;

import java.util.List;
import java.util.UUID;

import jmud.engine.object.JMudObject;

public class JMudEvent {

	private UUID eventID = null;
	private int eventType = -1;
	
    protected transient JMudObject source;
    protected List<JMudObject> targets;
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
        return this.source;
    }
    
    /**
     * The objects on which the Event is targeted.
     *
     * @return The object on which the Event initially occurred.
     */
    public List<JMudObject> getTargets(){
        return this.targets;
    }

	public UUID getEventID() {
		return this.eventID;
	}

	public int getEventType() {
		return eventType;
	}
    
    
}
