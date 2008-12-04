package jmud.engine.event;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * ToDo CM: is calling them nEvent misleading? could be JMudEventType.GetType, .GetEventType or simply JMudEventType.Get (i.e. we know it's a type of event)
 *
 * ToDo CM: We could potentially put in the events what classes of data are required in the data map
 *
 * @author David Loman
 */
public enum JMudEventType {
	SendToConsoleEvent,
    FailedEvent,
    GetEvent,
    GotEvent,
    OpenedEvent,
    OpenEvent,
    SuccessEvent,
    Trigger{
        public List<JMudEventTypeParameter> getRequiredDataTypes(){
            List<JMudEventTypeParameter> parameters = new ArrayList<JMudEventTypeParameter>();
            parameters.add(JMudEventTypeParameter.EVENT);
            return parameters;
        }
    },
    Attack,
    Attacked;

    /**
     * Specify what data objects are required in the event's data map
     *
     * You can return more than one of each Class to specify more than one object of that data type
     * @return a List of Classes that are required in the JMudEvent's data map
     */
    public List<JMudEventTypeParameter> getRequiredDataTypes(){
        return null;
    }
}
