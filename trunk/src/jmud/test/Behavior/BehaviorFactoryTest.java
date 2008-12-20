package jmud.test.Behavior;

import jmud.engine.behavior.AttackBehavior;
import jmud.engine.behavior.Behavior;
import jmud.engine.behavior.BehaviorFactory;
import jmud.engine.object.JMudObject;
import jmud.test.event.TestBehavior;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BehaviorFactoryTest{

    @Test
    public void testCreateBehavior(){
        JMudObject testJMudObject = new JMudObject();
        Behavior testBehavior = BehaviorFactory.createBehavior(TestBehavior.class, testJMudObject);
        Assert.assertNotNull("BehaviorFactory created null class", testBehavior);
        Assert.assertEquals("BehaviorFactory created instance of incorrect class", TestBehavior.class, testBehavior.getClass());
        Assert.assertSame("BehaviorFactory created instance of Behavior with incorrect JMudObject", testJMudObject, testBehavior.getOwner());
    }

    @Test
    public void testCreateNonBehavior(){
        Behavior nullBehavior = BehaviorFactory.createBehavior(JMudObject.class, new JMudObject());
        Assert.assertNull("BehaviorFactory created behavior from invalid class", nullBehavior);
    }

    @Test
    public void testCreateMulitpleBehaviors(){
        List<Class> behaviorClasses = Arrays.asList((Class) TestBehavior.class, AttackBehavior.class);
        List<Behavior> createdBehaviors = BehaviorFactory.createBehaviors(behaviorClasses, new JMudObject());
        Assert.assertEquals("BehaviorFactory created incorrect number of behaviors", createdBehaviors.size(), behaviorClasses.size());
        Assert.assertTrue("BehaviorFactory failed to create specified Behavior instances", instanceOfEachClassExists(behaviorClasses, createdBehaviors));
    }

    @Test
    public void testCreateMulitpleSameBehaviors(){
        List<Class> behaviorClasses = Arrays.asList((Class) TestBehavior.class, TestBehavior.class);
        List<Behavior> createdBehaviors = BehaviorFactory.createBehaviors(behaviorClasses, new JMudObject());
        Assert.assertEquals("BehaviorFactory created incorrect number of behaviors", createdBehaviors.size(), behaviorClasses.size());
        Assert.assertEquals("BehaviorFactory failed to create specified Behavior instance", createdBehaviors.get(0).getClass(), TestBehavior.class);
        Assert.assertEquals("BehaviorFactory failed to create specified Behavior instance", createdBehaviors.get(1).getClass(), TestBehavior.class);
    }

    private boolean instanceOfEachClassExists(List<Class> behaviorClasses, List<Behavior> behaviorInstances){
        for(Class behaviorClass : behaviorClasses){
            if(!instanceOfClassExists(behaviorClass, behaviorInstances)){
                return false;
            }
        }
        return true;
    }

    private boolean instanceOfClassExists(Class behaviorClass, List<Behavior> behaviorInstances){
        for(Behavior behavior : behaviorInstances){
            if(behavior.getClass().equals(behaviorClass)){
                return true;
            }
        }
        return false;
    }

    @Test
    public void testCreateBehaviorWithNullJMudObject(){
        Assert.assertNull("BehaviorFactory created Behavior instance with null JMudObject", BehaviorFactory.createBehavior(TestBehavior.class, null));
    }

    @Test
    public void testCreateBehaviorsWithNullJMudObject(){
        Assert.assertEquals("BehaviorFactory created Behavior instances with null JMudObject", BehaviorFactory.createBehaviors(Arrays.asList((Class)TestBehavior.class, AttackBehavior.class), null).size(), 0);
    }

    @Test
    public void testCreateBehaviorWithNullBehaviorClass(){
        Assert.assertNull("BehaviorFactory created Behavior instance with null Behavior class", BehaviorFactory.createBehavior(null, new JMudObject()));
    }

    @Test
    public void testCreateBehaviorsWithNullBehaviorClassList(){
        Assert.assertEquals("BehaviorFactory created Behavior instances with null Behavior class list", BehaviorFactory.createBehaviors(null, new JMudObject()).size(), 0);
    }

    @Test
    public void testCreateBehaviorsWithEmptyBehaviorClassList(){
        Assert.assertEquals("BehaviorFactory created Behavior instances with null Behavior class list", BehaviorFactory.createBehaviors(new ArrayList<Class>(), new JMudObject()).size(), 0);
    }
}
