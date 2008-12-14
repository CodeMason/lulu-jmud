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
        Assert.assertEquals("JMudObject \"bag\" was not transferred to JMudObject \"pcSteve\"", bag.getParentObject(), pcSteve);
    }

    @Test
    public void testRemoveJMudObjectParent(){
        bag.orphan();
        Assert.assertNull("JMudObject \"bag\" was not removed from JMudObject \"pcSteve\"", bag.getParentObject());
    }

    @Test
    public void testAddJMudObjectParent(){
        bag.changeParent(null);
        bag.changeParent(orc0);
        Assert.assertEquals("JMudObject \"bag\" was not transferred to JMudObject \"orc0\"", bag.getParentObject(), orc0);
    }

    @Test
    public void testDropJMudObjectToParent(){
        bag.changeParent(pcSteve);
        bag.changeParent(pcSteve.getParentObject());
        Assert.assertEquals("JMudObject \"bag\" was not \"dropped\" to JMudObject \"room\" from JMudObject \"pcSteve\"", bag.getParentObject(), pcSteve.getParentObject());
    }

    private void getTestJMudObjects(JMudObject root){
        bag = root.getChildObject("room").getChildObject("bag");
        pcSteve = root.getChildObject("room").getChildObject("pcSteve");
        orc0 = root.getChildObject("room").getChildObject("orc0");
    }

}
