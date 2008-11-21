package jmud.command;

import jmud.netIO.PlayerChannel;
import jmud.rooms.Room;


/**
 *  Extends the Command class to provide
 *          the functionality for a player to move to the
 *          room below the current room
 *
 * Created on March 28 2003 12:26 PM
 */
public class Down extends Direction {

    private static final String TARGET_DIRECTION = "down";
    private static final String SOURCE_DIRECTION = "above";

    private Room room;

    public Down(PlayerChannel pc, Room r, String target) {
        super(pc, r);
        this.room = r;
    }

    public Room getTargetRoom() {
        return room.down;
    }

    public String getTargetDirection() {
        return TARGET_DIRECTION;
    }

    public String getSourceDirection() {
        return SOURCE_DIRECTION;
    }

}