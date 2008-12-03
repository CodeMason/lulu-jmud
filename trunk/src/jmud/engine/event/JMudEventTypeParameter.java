package jmud.engine.event;

/**
 *
 * ToDo CM: is calling them nEvent misleading? could be JMudEventType.GetType, .GetEventType or simply JMudEventType.Get (i.e. we know it's a type of event)
 *
 * ToDo CM: We could potentially put in the events what classes of data are required in the data map
 *
 * @author David Loman
 */
public enum JMudEventTypeParameter{

    EVENT("EVENT", JMudEvent.class);

    private String name;
    private Class clazz;

    JMudEventTypeParameter(String name, Class clazz){
        this.name = name;
        this.clazz = clazz;
    }

    public String getName(){
        return this.name;
    }

    public Class getClazz(){
        return this.clazz;
    }
}