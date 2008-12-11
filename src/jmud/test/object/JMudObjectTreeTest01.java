package jmud.test.object;

import jmud.engine.object.JMudObject;
import jmud.test.CommonTestMethods;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JMudObjectTreeTest01 {
    private JMudObject bag;
    private JMudObject pcSteve;
    private JMudObject orc0;

    @Before
    public void setup(){
        getTestJMudObjects(CommonTestMethods.buildSimpleJMudObjectTree());
    }

    @Test
    public void testChangeJMudObjectParent() {
        bag.changeParent(pcSteve);
        Assert.assertEquals("JMudObject \"bag\" was not transferred to JMudObject \"pcSteve\"", bag.getParent(), pcSteve);
    }

    @Test
    public void testRemoveJMudObjectParent(){
        bag.changeParent(null);
        Assert.assertNull("JMudObject \"bag\" was not removed from JMudObject \"pcSteve\"", bag.getParent());
    }

    @Test
    public void testAddJMudObjectParent(){
        bag.changeParent(null);
        bag.changeParent(orc0);
        Assert.assertEquals("JMudObject \"bag\" was not transferred to JMudObject \"orc0\"", bag.getParent(), orc0);
    }

    @Test
    public void testDropJMudObjectToParent(){
        bag.changeParent(pcSteve);
        bag.changeParent(pcSteve.getParent());
        Assert.assertEquals("JMudObject \"bag\" was not \"dropped\" to JMudObject \"room\" from JMudObject \"pcSteve\"", bag.getParent(), pcSteve.getParent());
    }

    private void getTestJMudObjects(JMudObject root){
        bag = root.childrenGet("room").childrenGet("bag");
        pcSteve = root.childrenGet("room").childrenGet("pcSteve");
        orc0 = root.childrenGet("room").childrenGet("orc0");
    }

}
