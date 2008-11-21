package jmud.rooms;

import java.sql.SQLException;

import jmud.dbio.MysqlConnector;
import jmud.mobs.MobType;

/**
 * Handles interaction with the MUD world in the form of a graph of rooms
 *
 * Created on April 22, 2002, 10:28 PM
 */
public class MudMap {

    private static int iStartRoom;
    private Room[] rooms;

    public MudMap() {
    }

    /**
     * Creates new Map with default player starting position
     *
     * @param startingRoom room to start players in
     */
    public MudMap(int startingRoom) {
        iStartRoom = startingRoom;
    }

    /**
     * Loads rooms from the database
     */
    public void loadRooms() {
        MysqlConnector mSqlConn = new MysqlConnector();
        try {
            mSqlConn.setup();
        } catch(Exception e) {
            System.out.println("loadRooms() --> No rooms loaded, could not connect to database.");
            System.out.println(e);
            return;
        }
        try {
            rooms = new Room[mSqlConn.getRoomCount()];
            mSqlConn.getRooms(rooms);
            mSqlConn.getRoomConnections(rooms);
            mSqlConn.close();
        } catch(SQLException se) {
            System.out.println("loadRooms() --> " + se);
        }
        mSqlConn = null;

        System.out.print("Setting Exits: ");
        for(Room room : rooms) {
            if(room != null) {
                room.setExits();
            }
            System.out.print(".");
        }
        System.out.print("\n");
    }

    /**
     * Load all the <code>Mob</code>s and their <code>MobType</code>s into their
     * <code>Room</code>s.
     */
    public void loadMobs() {
        MysqlConnector mSqlConn = new MysqlConnector();
        try {
            mSqlConn.setup();
        } catch(Exception e) {
            System.out.println("loadMobs() --> No mobs loaded, could not connect to database.");
            System.out.println(e);
            return;
        }
        try {
            MobType[] mobTypes = new MobType[mSqlConn.getMobTypeCount()];
            //System.out.println("Size of MobType Array is " + mobTypes.length);
            mSqlConn.getMobTypes(mobTypes);
            // adds uses the mob types to get the mobs from the database and load them into rooms
            mSqlConn.getMobs(rooms, mobTypes);
            mSqlConn.close();
        } catch(SQLException se) {
            System.out.println("loadMobs() --> " + se);
        }
        mSqlConn = null;
    }

    public Room getStart() {
        //System.out.println("Returning room " + iStartRoom);
        return rooms[iStartRoom];
    }

}
