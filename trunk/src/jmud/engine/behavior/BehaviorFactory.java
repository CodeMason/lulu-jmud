package jmud.engine.behavior;

import jmud.engine.object.JMudObject;

import java.util.ArrayList;
import java.util.List;

public class BehaviorFactory{

    public static Behavior createBehavior(Behavior behavior){
        Behavior newBehavior = createBehavior(behavior.getClass(), behavior.getOwner());

        if(newBehavior == null){
            System.out.println("Could not create behavior from class, cloning instead");
            newBehavior = behavior.clone();
        }

        return newBehavior;
    }
    
    public static Behavior createBehavior(Class behaviorClass, JMudObject owner){
        Behavior newBehavior = null;

        if(isBehaviorClass(behaviorClass) && owner != null){
            newBehavior = createBehaviorFromClass(behaviorClass, owner);
        }

        return newBehavior;
    }


    public static List<Behavior> createBehaviorsFromClasses(List<Class> behaviorClasses, JMudObject behaviorOwner){
        List<Behavior> createdBehaviors = new ArrayList<Behavior>();
        Behavior createdBehavior;
        if(behaviorClasses != null){
            for(Class behaviorClass : behaviorClasses){
                createdBehavior = createBehaviorFromClass(behaviorClass, behaviorOwner);
                if(createdBehavior != null){
                    createdBehaviors.add(createdBehavior);
                }
            }
        }
        return createdBehaviors;
    }

    private static boolean isBehaviorClass(Class behaviorClass){
        return behaviorClass != null && Behavior.class.isAssignableFrom(behaviorClass);
    }

    private static Behavior createBehaviorFromClass(Class behaviorClass, JMudObject behaviorOwner){
        Behavior defaultBehavior = null;

        if(behaviorOwner != null){
            try{
                defaultBehavior = (Behavior) behaviorClass.getConstructor(JMudObject.class).newInstance(behaviorOwner);
            } catch(Exception e){
                System.out.println("Default constructor (Class, JMudObject) not found: behavior of type " + behaviorClass.getName() + " not created: \n" + e);
            }
        }
        return defaultBehavior;
    }
}
