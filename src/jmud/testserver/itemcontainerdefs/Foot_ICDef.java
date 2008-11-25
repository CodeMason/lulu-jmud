/*
 * Hand.java
 *
 * Created on December 9, 2007, 5:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jmud.testserver.itemcontainerdefs;

/**
 * @author root
 */
public class Foot_ICDef extends AbstractItemContainerDef {
    private static final int MAX_BULK = 20;

    /**
     * Creates a new instance of Foot
     */
    public Foot_ICDef(int Id, String name) {
        super(Id, name);
    }

    public int maxBulk() {
        return MAX_BULK;
    }

}
