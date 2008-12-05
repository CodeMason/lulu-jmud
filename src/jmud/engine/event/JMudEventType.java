package jmud.engine.event;

import java.util.ArrayList;
import java.util.List;

/**
 * ToDo CM: We could potentially put in the events what classes of data are required in the data map
 *
 * @author David Loman
 */
public enum JMudEventType {
    SendToConsole,
    Get, Got,
    Open, Opened,
    Trigger{
        public List<JMudEventTypeParameter> getRequiredDataTypes(){
            List<JMudEventTypeParameter> parameters = new ArrayList<JMudEventTypeParameter>();
            parameters.add(JMudEventTypeParameter.EVENT);
            return parameters;
        }
    },
    Attack, Attacked;

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
