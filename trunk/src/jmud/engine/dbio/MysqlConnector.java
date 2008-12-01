package jmud.engine.dbio;

import jmud.engine.character.Character;
import jmud.engine.mobs.MobType;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides access to the database and functions to return specific data from
 * the database
 * 
 * Created on April 30, 2002, 9:49 PM
 * 
 * ToDo: finish the list of attributes in loadAttrIDs() ToDo: finish the
 * loadModIDs() function
 */
@SuppressWarnings( { "ObjectAllocationInLoop" })
public class MysqlConnector {

	// TODO Commented out the whole class since its doing nothing but generating
	// compile errors right now.
	// FIXME Rework the Database IO layer after the basic mud data structs are
	// in place.

	protected Connection conn;
	protected ResultSet rs;
	protected static String dbUrl = "jdbc:mysql:///jmud?user=root&password=";
	protected Statement stmt;

	private final int ATTRIBUTE_PARAM_INDEX = 1;
	private final int MODIFIER_PARAM_INDEX = 2;
	private final int ATTRIBUTE_COLUMN_INDEX = 1;
	private final int MODIFIER_COLUMN_INDEX = 2;
	private final int PLAYERID_PARAM_INDEX = 1;

	int iStrengthAttributeID;
	int iDexterityAttributeID;
	int iDamageModifierID;

	public MysqlConnector() {
	}

	/**
	 * Create a connection to the database and a database statement which is the
	 * equivalent of a ADODB Command object (I think)
	 * 
	 * @throws Exception
	 *             if something fails creating the db driver
	 */
	public void setup() throws Exception {
		/*
		 * use the default constructor for the jdbc driver class to create a new
		 * instance to obtain a connection object
		 * 
		 * the driver package com directory must be in the same directory as the
		 * jmud source
		 */
//		Class.forName("com.mysql.jdbc.Driver").newInstance();
//		conn = DriverManager.getConnection(dbUrl);
		// stmt = conn.createStatement();

		// prepare a callable statement for each of our stored procedures
		/*
		 * http://dev.mysql.com/doc/refman/5.0/en/connector-j-usagenotes-basic.html
		 * mentions that callable statements should be reused, however I
		 * couldn't find a way to reuse callable statements across stored
		 * procedures. I assume the author meant to reuse the same callable
		 * statement for that particular sproc.
		 * 
		 * Forget that: if we're going to create the callable statements every
		 * time we get a connection then we might as well just create the
		 * statements we know we're going to use. This should save us time as
		 * we're not recreating callable statements that we're not going to use
		 * all the time.
		 */
	}

	/**
	 * Close the recordset and connection objects, in that order.
	 */
	public void close() {
//		if (rs != null) {
//			try {
//				rs.close();
//			} catch (SQLException SQLE) {
//				//
//			}
//		}
//		if (conn != null) {
//			try {
//				conn.close();
//			} catch (SQLException SQLE) {
//				//
//			}
//		}
	}

	/**
	 * Find all the attribute IDs in the database and assign them to local
	 * variables so that attributes can be referred to by variable rather than
	 * constant
	 * 
	 * @throws SQLException
	 *             if something goes awry with the db calls
	 */
	public void loadAttrIDs() throws SQLException {
//		String strAttribute;
//		int iAttributeID;
//
//		System.out.print("loading attribute IDs: ");
//
//		// create and execute a callable statement representing a stored
//		// procedure
//		rs = conn.prepareCall("{call spGetAttributes}").executeQuery();
//
//		// loop through all the records returned
//		while (rs.next()) {
//
//			// get each attribute name and ID
//			strAttribute = rs.getString(1);
//			iAttributeID = rs.getInt(2);
//
//			// assign the attributes to their variables
//			if ("strength".equalsIgnoreCase(strAttribute)) {
//				iStrengthAttributeID = iAttributeID;
//				// DEBUG:
//				System.out.print(".");
//			} else if ("dexterity".equalsIgnoreCase(strAttribute)) {
//				iDexterityAttributeID = iAttributeID;
//				// DEBUG:
//				System.out.print(".");
//			} else {
//				// DEBUG: print a marker showing we found an attribute we
//				// weren't expecting
//				System.out.print("?");
//			}
//		}
//		System.out.print("\n");
//
//		rs.close();
	}

	/**
	 * ERROR! This function was copied from loadAttriIDs() but never completed
	 * <p/> Find all the modifier IDs in the database and assign them to local
	 * variables so that modifiers can be referred to by variable rather than
	 * constant
	 */
	public void loadModIDs() throws SQLException {
//		String strModifier;
//		int iModifierID;
//
//		// DEBUG:
//		System.out.print("loading modifier IDs: ");
//
//		// run the attribute query
//		// rs = stmt.executeQuery(GET_ATTRIBUTE_IDS);
//		// rs = cstmtGetMobs.executeQuery();
//
//		// loop through all the attributes we found
//		while (rs.next()) {
//			strModifier = rs.getString(1);
//			iModifierID = rs.getInt(2);
//
//			if ("damage".equalsIgnoreCase(strModifier)) {
//				iDamageModifierID = iModifierID;
//				System.out.print(".");
//			} else {
//				// print a marker showing we found a modifier we weren't
//				// expecting
//				System.out.print("?");
//			}
//		}
//		System.out.print("\n");
//
//		rs.close();
	}

	/**
	 * Load the Strength->Damage modifier array <br>
	 * Note: Call loadAttrIDs and loadModIDs FIRST!!
	 * 
	 * @see MysqlConnector#loadAttrIDs
	 * @see MysqlConnector#loadModIDs
	 */
	public void loadModsStrDmg(int[] iStrDmgMods) throws SQLException {
//		int iMaxAttrLvl = 0;
//
//		// DEBUG:
//		System.out.println("loading Strengh-Damage modifiers");
//
//		// create and execute a callable statement representing a stored
//		// procedure
//		CallableStatement cstmtGetModifiers = conn.prepareCall("{call spGetModifiers(?,?)}");
//		cstmtGetModifiers.setInt(ATTRIBUTE_PARAM_INDEX, iStrengthAttributeID);
//		cstmtGetModifiers.setInt(MODIFIER_PARAM_INDEX, iDamageModifierID);
//
//		// run the statement
//		// rs = pstmt.executeQuery();
//		// run the sproc
//		rs = cstmtGetModifiers.executeQuery();
//
//		// find out how big to make the array by getting the
//		// maximum attribute level (because each attribute level
//		// needs to have a spot in the modifier array, even it only
//		// occupies that spot with a zero
//		while (rs.next()) {
//			if (rs.getInt(1) > iMaxAttrLvl) {
//				iMaxAttrLvl = rs.getInt(2);
//			}
//		}
//
//		// instantiate a big enough array
//		iStrDmgMods = new int[iMaxAttrLvl];
//
//		// hopefully this sets the pointer before the first element
//		rs.beforeFirst();
//
//		// loop through all the damage modifiers and add them to the array
//		while (rs.next()) {
//			// add a modifier to the array at the index of the attribute level
//			// that it applies to
//			iStrDmgMods[rs.getInt(ATTRIBUTE_COLUMN_INDEX)] = rs.getInt(MODIFIER_COLUMN_INDEX);
//		}
//
//		rs.close();
	}

	/**
	 * Return the number of <code>Room</code> records stored/persisted in the
	 * database. <p/> I say stored or persisted because at this point the only
	 * way to get room information into the database is to enter it manually or
	 * via a script file, but the plan is to have in game editing. The in game
	 * editing is close, but not quite there.
	 * 
	 * @return The number of rooms stored in the database.
	 * @see MysqlConnector#getRooms(Room[])
	 */
	public int getRoomCount() throws SQLException {
		int iRooms = 0;
//
//		// create and execute callable statment for stored procedure
//		rs = conn.prepareCall("{call spGetRoomCount}").executeQuery();
//
//		// check for one returned record
//		if (rs.next()) {
//			// get the max room ID, and then add 1 to account for Array length
//			// being greater
//			// than Array max index
//			iRooms = rs.getInt(1) + 1;
//		} else {
//			iRooms = 0;
//		}
//
//		rs.close();
//
		return iRooms;
	}

	/**
	 * Return the number of <code>Room</code> records that a player has visited
	 * and can remember
	 * 
	 * @return The number of rooms this player can remember
	 * @throws SQLException
	 */
	public int getPlayerRoomCount(int iPlayerID) throws SQLException {
		int iRooms = 0;
//
//		// create and execute a callable statement for a stored procedure
//		CallableStatement cstmtGetPlayerRoomCount = conn.prepareCall("{call spGetPlayerRoomCount(?)}");
//		cstmtGetPlayerRoomCount.setInt(PLAYERID_PARAM_INDEX, iPlayerID);
//		rs = cstmtGetPlayerRoomCount.executeQuery();
//
//		// loop through all of the returned records
//		if (rs.next()) {
//			// unlike getRoomCount we're going to actually get the number of
//			// player rooms, not the max room ID for that player's Player-rooms
//			iRooms = rs.getInt(1);
//		} else {
//			iRooms = 0;
//		}
//
//		rs.close();
//
//		// make sure we don't return less than zero
		return iRooms < 0 ? 0 : iRooms;
	}

	/**
	 * Retrieve all the room connections out of the database and go through the
	 * Room array adding all the connecting rooms to each room in the array.
	 * 
	 * @param rooms
	 *            The previously created <code>Room</code> array with all the
	 *            rooms in it
	 * @see MysqlConnector#getRooms(Room[])
	 */
	/**
	 * This is the first attempt at updating the game world from in the game
	 * 
	 * @param iRoomID
	 *            The Room object to update
	 * @param strDesc
	 *            The new short description for the Room object
	 * @throws SQLException
	 *             on statment creation or update execution
	 */
	public void setRoomShortDesc(int iRoomID, String strDesc) throws SQLException {
//		int iResult;
//		iResult = conn.createStatement().executeUpdate(
//				"update tblRooms set vchDescShort = '" + strDesc + "' where iRoomID = " + iRoomID);
//		System.out.println(iResult);
//
//		rs.close();
	}

	/**
	 * Fill a supplied LinkedList with player object data queried from the
	 * database.
	 * 
	 * @param playerList
	 *            The <code>LinkedList</code> to append new <code>Player</code>
	 *            objects to.
	 * @throws SQLException
	 *             on sproc prepare, sproc execute or recordset close
	 */
	public void getPlayers(LinkedList<Character> playerList) throws SQLException {
//
//		// create and execute the a stored procedure through a callable sql
//		// statement
//		rs = conn.prepareCall("{call spGetPlayers}").executeQuery();
//
//		// loop through the returned records and create Players from the records
//		// to add to the passed in list of players
//		while (rs.next()) {
//			// noinspection ObjectAllocationInLoop
//			// FIXME Had to stubb this SQL insert due to Massive Architecture
//			// rework.
//			// playerList.add(new Character(rs.getInt(1),
//			// rs.getString(2),
//			// rs.getString(3),
//			// rs.getString(4),
//			// rs.getString(5),
//			// rs.getInt(6),
//			// rs.getInt(7),
//			// rs.getInt(8),
//			// rs.getInt(9),
//			// rs.getInt(10),
//			// rs.getInt(11)));
//
//			// DEBUG:
//			// noinspection ObjectAllocationInLoop
//			System.out.println(new StringBuilder().append(playerList.getLast().getName()).append(" loaded \n")
//					.toString());
//		}
//
//		rs.close();
	}

	/**
	 * Get a player from the database if one exists that matches a login and
	 * password
	 * 
	 * @param login
	 *            player login to use (i.e. player name)
	 * @param password
	 *            player password
	 * @return the player from the database
	 * @throws SQLException
	 *             on sproc prepares, sproc executes, recordset close, sproc
	 *             param assignments
	 */
	@SuppressWarnings( { "ObjectAllocationInLoop" })
	public Character getCharacter(String login, String password) throws SQLException {
//		int playerID;
//		List<Slot> slots = new LinkedList<Slot>();
//		List<String> aliases = new LinkedList<String>();
//		List<Slot> itemSlots;
//
//		// create a callable statement to get the player's slots once we have
//		// the players ID
//		CallableStatement cstmtGetPlayerSlots = conn.prepareCall("{call spGetPlayerSlots(?)}");
//		CallableStatement cstmtGetSlotAliases = conn.prepareCall("{call spGetSlotAliases(?)}");
//		CallableStatement cstmtGetPlayerSlotItems = conn.prepareCall("{call spGetPlayerSlotItems(?, ?)}");
//
//		// create and execute the a stored procedure through a callable sql
//		// statement
//		CallableStatement cstmtGetPlayer = conn.prepareCall("{call spGetPlayer(?,?)}");
//		cstmtGetPlayer.setString(1, login);
//		cstmtGetPlayer.setString(2, password);
//		rs = cstmtGetPlayer.executeQuery();
//
//		// loop through the returned records and create Players from the records
//		// to add to the passed in list of players
//		if (rs.next()) {
//			playerID = rs.getInt(1);
//
//			// FIXME Had to stubb this SQL insert due to Massive Architecture
//			// rework.
//			// player = new Character(playerID,
//			// rs.getString(2),
//			// rs.getString(3),
//			// rs.getString(4),
//			// rs.getString(5),
//			// // not using class ID
//			// rs.getInt(7),
//			// rs.getInt(8),
//			// rs.getInt(9),
//			// rs.getInt(10),
//			// rs.getInt(11),
//			// rs.getInt(12));
//
//			// DEBUG:
//			// System.out.println(player.getName() + " loaded \n");
//			rs.close();
//		} else {
//			rs.close();
//			return null;
//		}
//
//		// now that we have the player ID we can get the slots
//		cstmtGetPlayerSlotItems.setInt(1, playerID);
//		cstmtGetPlayerSlots.setInt(1, playerID);
//		rs = cstmtGetPlayerSlots.executeQuery();
//
//		// DEBUG:
//		System.out.print("Getting player slots: ");
//
//		while (rs.next()) {
//			try {
//				Class clazz = Class.forName(rs.getString(2));
//				// noinspection unchecked
//				Constructor<Slot> constructor = clazz.getConstructor(new Class[] { Integer.TYPE, String.class });
//
//				// noinspection ObjectAllocationInLoop
//				slots.add(constructor.newInstance(rs.getInt(3), rs.getString(1)));
//
//				// DEBUG:
//				System.out.print(".");
//			} catch (Exception e) {
//				System.out.println("Failed to load player slot \n" + e.getMessage());
//			}
//		}
//
//		rs.close();
//
//		System.out.println("\n");
//
//		for (Slot slot : slots) {
//			// DEBUG:
//			// System.out.print("Getting player slots aliases and items: ");
//
//			aliases.clear();
//			cstmtGetSlotAliases.setInt(1, slot.getId());
//			rs = cstmtGetSlotAliases.executeQuery();
//			while (rs.next()) {
//				try {
//					aliases.add(rs.getString(1));
//
//					// DEBUG:
//					// System.out.print("a");
//				} catch (Exception e) {
//					System.out.println("Failed to load player slot \n");
//				}
//			}
//			rs.close();
//			slot.setAliases(aliases);
//			// FIXME Had to stubb this SQL insert due to Massive Architecture
//			// rework.
//			// player.addSlot(slot);
//
//			// set the slot ID param and get the items for that player and slot
//			cstmtGetPlayerSlotItems.setInt(2, slot.getId());
//
//			// System.out.println("getting player slot items for player " +
//			// playerID + " and slot " + ((Integer)o[1]).toString());
//
//			rs = cstmtGetPlayerSlotItems.executeQuery();
//			while (rs.next()) {
//				// add the current slot to the list of slots that this item can
//				// go in
//				itemSlots = new LinkedList<Slot>();
//				itemSlots.add(slot);
//
//				slot.addItem(new AbstractItemDef(rs.getInt(1), rs.getString(8), rs.getInt(4), rs.getInt(5), rs
//						.getInt(3), rs.getInt(6), itemSlots));
//
//				// DEBUG:
//				// System.out.println("Item name: " +
//				// ((Slot)o[0]).getItems().get(0).getName());
//				// System.out.println("Name is " + rs.getString(5));
//
//				// DEBUG:
//				// System.out.print("i");
//			}
//			rs.close();
//
//			// System.out.println("\n");
//		}
//
//		rs.close();

		// return player;
		return new Character(0, "", "");
	}

	/**
	 * Create <code>MobType</code> objects in an Array indexed by their ID so
	 * that <code>Mob</code> objects can be created and given a reference to
	 * their <code>MobType</code>s.
	 * 
	 * @param mobTypes
	 *            The previously instantiated (but not neccessarily initialized)
	 *            array of type <code>MobType</code> that has be instantiated to
	 *            hold at least as many <code>MobTypes</code> as the maximum
	 *            <code>MobType</code> ID. (There will be empty spaces in the
	 *            array but I'm not overly worried about that now as the
	 *            <code>MobType</code> Array is only a temporary object (NOT a
	 *            temporary solution! <grin>).
	 * @throws SQLException
	 *             on sproc prepare, sproc exexcute or recordset close
	 */
	public void getMobTypes(MobType mobTypes[]) throws SQLException {
//		// DEBUG:
//		System.out.print("loading mob types: ");
//
//		// create and execute a callable statement for a stored procedure
//		rs = conn.prepareCall("{call spGetMobTypes}").executeQuery();
//
//		// loop through the resultant records
//		while (rs.next()) {
//
//			// DEBUG:
//			System.out.print(".");
//
//			// construct a new MobType and add it to the Array
//			// according to the MobTypes ID.
//			mobTypes[rs.getInt(1)] = new MobType(rs.getString(2), rs.getString(3), rs.getString(4));
//		}
//		System.out.print("\n");
//
//		rs.close();
	}

	/**
	 * Return the number of <code>MobType</code> records stored/persisted in the
	 * database. <p/> I say stored or persisted because at this point the only
	 * way to get MobType information into the database is to enter it manually
	 * or via a script file, but the plan is to have in game editing. The in
	 * game editing is close, but not quite there.
	 * 
	 * @return The number of <code>MobType</code>s stored in the database.
	 * @throws SQLException
	 *             on sproc prepare, sproc execute or rs close
	 * @see MysqlConnector#getMobTypes(MobType[])
	 */
	public int getMobTypeCount() throws SQLException {
		int iMobTypes = 0;

//		// create and execute a callable statement representing a stored
//		// procedure
//		rs = conn.prepareCall("{call spGetMobTypeCount}").executeQuery();
//
//		// look at the first record returned, if any
//		if (rs.next()) {
//
//			// get the max MobType ID, and then add 1 to account for Array
//			// length being greater
//			// than Array max index
//			iMobTypes = rs.getInt(1) + 1;
//
//			// else we didn't find *any* mob types, so the max is zero
//		} else {
//			iMobTypes = 0;
//		}
//
//		rs.close();

		return iMobTypes;
	}

	/**
	 * Populate a <code>List</code> with Command + Alias pairs.
	 * 
	 * @param commands
	 *            list of commands to get aliases for
	 * @throws SQLException
	 *             if the stored procedure prepare call screws up
	 */
	public void getCommandAliases(List<String[]> commands) throws SQLException {
//
//		rs = conn.prepareCall("{call spGetCommandAliasPairs}").executeQuery();
//
//		// loop through the resultant records
//		while (rs.next()) {
//			/*
//			 * Create a new array with a Command + Alias pair and add it to the
//			 * <code>List</code>
//			 */
//			commands.add(new String[] { rs.getString(1), rs.getString(2) });
//		}
//
//		rs.close();
	}

	/**
	 * update a player's room in the database
	 * 
	 * @param iPlayerID
	 *            player to insert into room
	 * @param iRoomID
	 *            room to insert player into
	 * @throws SQLException
	 *             creating the sproc statement, setting the values, calling the
	 *             sproc
	 */
	public void insertPlayerRoom(int iPlayerID, int iRoomID) throws SQLException {
//		CallableStatement cstmtInsertPlayerRoom = conn.prepareCall("{call spInsertPlayerRoom(?,?)}");
//		cstmtInsertPlayerRoom.setInt(1, iPlayerID);
//		cstmtInsertPlayerRoom.setInt(2, iRoomID);
//		cstmtInsertPlayerRoom.executeUpdate();
	}

	public void getSlotAliases(HashMap<String, String> slotAliases) throws SQLException {
//
//		// DEBUG:
//		System.out.print("Getting slot aliases: ");
//
//		// create and execute the a stored procedure through a callable sql
//		// statement
//		rs = conn.prepareCall("{call spGetSlotAliases}").executeQuery();
//
//		// loop through the returned records and create Players from the records
//		// to add to the passed in list of players
//		while (rs.next()) {
//			slotAliases.put(rs.getString(1), rs.getString(2));
//
//			// DEBUG:
//			System.out.print(".");
//		}
//
//		// DEBUG:
//		System.out.println("");
//
//		rs.close();
	}

	/**
	 * Used for stand-alone testing of database queries
	 * 
	 * @param args
	 *            the command line arguments
	 */
	/*
	 * keeping this for testing public static void main (String args[]) { Room
	 * rooms[]; MysqlConnector mSqlConn = new MysqlConnector();
	 * mSqlConn.setup(); try{ rooms = new Room[mSqlConn.getRoomCount()];
	 * mSqlConn.getRooms(rooms); mSqlConn.getRoomConnections(rooms);
	 * mSqlConn.close(); }catch(SQLException se){ System.out.println(se); } }
	 */
}
