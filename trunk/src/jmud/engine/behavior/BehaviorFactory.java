package jmud.engine.behavior;

import jmud.engine.object.JMudObject;

import java.util.ArrayList;
import java.util.List;

public class BehaviorFactory{

    public static Behavior createBehavior(Class behaviorClass, JMudObject behaviorOwner){
        if(isBehaviorClass(behaviorClass) && behaviorOwner != null){
            return createBehaviorFromClass(behaviorClass, behaviorOwner);
        }else{
            return null;
        }
    }

    public static List<Behavior> createBehaviors(List<Class> behaviorClasses, JMudObject behaviorOwner){
        List<Behavior> createdBehaviors = new ArrayList<Behavior>();
        Behavior createdBehavior;
        if(behaviorClasses != null){
            for(Class behaviorClass : behaviorClasses){
                createdBehavior = createBehavior(behaviorClass, behaviorOwner);
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
        Behavior defaultBehavior;
        try{
            defaultBehavior = (Behavior) behaviorClass.getConstructor(JMudObject.class).newInstance(behaviorOwner);
        } catch(Exception e){
            System.out.println("Could not instantiate instance of Behavior\n" + e);
            defaultBehavior = null;
        }
        return defaultBehavior;
    }
}
