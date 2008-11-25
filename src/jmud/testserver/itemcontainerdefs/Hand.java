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
 * @author Chris Maguire
 */
public class Hand extends SingleSlot {
    private static final boolean IS_GRABBER = true;

    /**
     * Creates a new instance of Hand
     *
     * @param name the name of the hand (e.g. left hand)
     */
    public Hand(int Id, String name) {
        super(Id, name);
    }

    public int maxBulk() {
        return MAX_BULK;
    }

    public boolean isGrabber() {
        return IS_GRABBER;
    }
}
