package jmud.engine.dbio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import jmud.engine.account.Account;
import jmud.engine.character.PlayerCharacter;
import jmud.engine.core.JMudStatics;

/**
 * Provides access to the database and functions to return specific data from
 * the database.
 * 
 * @author David Loman
 */

public class MysqlConnection {

	// TODO Commented out the whole class since its doing nothing but generating
	// compile errors right now.
	// FIXME Rework the Database IO layer after the basic mud data structs are
	// in place.

	/*                                      */
	/*                                      */
	/* Generic Utility Methods */
	/*                                      */
	/*                                      */

	/**
	 * Use values from JMudStatics and create a DB connection.
	 * 
	 * @return the new database connection.
	 */
	public static Connection makeNewConnection() {
		Connection c;
		/*
		 * use the default constructor for the jdbc driver class to create a new
		 * instance to obtain a connection object the driver package com
		 * directory must be in the same directory as the jmud source
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			c = DriverManager.getConnection(JMudStatics.dbUrl, JMudStatics.dbUName, JMudStatics.dbPassWd);

			return c;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * DB access method for SQL statements that will return a ResultSet.
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 */
	public static ResultSet Query(Connection conn, String sql) {
		Statement s;
		ResultSet rs;

		try {
			s = conn.createStatement();
			rs = s.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * DB sccess method for SQL statements that do NOT return any data except
	 * the number of Rows Modified.
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 */
	public static int Update(Connection conn, String sql) {
		Statement s;
		int rs;

		try {
			s = conn.createStatement();
			rs = s.executeUpdate(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/*                                      */
	/*                                      */
	/* Specific DataGet Methods */
	/*                                      */
	/*                                      */

	/**
	 * Takes two strings and returns an int that represents the AccountID A
	 * return value of -1 means validation failed.
	 */
	public static int verifyLogin(String uname, String passwd) {
		// Connection c = MysqlConnection.makeNewConnection();
		//
		// String sql = "SELECT id, passwd FROM Accounts WHERE (uname='" + uname
		// + "');";
		// ResultSet rs = MysqlConnection.Query(c, sql);
		// int accountID = -1;
		//		
		// try {
		// while (rs.next()) {
		// String dbpd = rs.getString("passwd");
		// if (dbpd.equals(passwd)) {
		// accountID = rs.getInt("id");
		// }
		//
		// // can't think of a better way to make
		// // sure there is only ONE is row processed.
		// break;
		// }
		//			
		// rs.close();
		// c.close();
		//
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		//
		// return accountID;

		// TODO hook in the DB query here.

		// Temporary validation
		if (uname.equals(JMudStatics.hardcodeUName) && passwd.equals(JMudStatics.hardcodePasswd)) {
			return 42;
		} else {
			return -1;
		}
	}

	public static Map<String, PlayerCharacter> getCharactersByAccountID(int accountID) {
		Map<String, PlayerCharacter> chars = new HashMap<String, PlayerCharacter>();

		// Connection c = MysqlConnection.makeNewConnection();
		// String sql = "SELECT * FROM Characters WHERE (accountid='" +
		// accountID + "');";
		// ResultSet rs = MysqlConnection.Query(c, sql);

		// try {
		//
		//
		// //ALL THIS DEPENDS ON THE DB SCHEMA.
		//			
		// rs.close();
		// c.close();
		//
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// Temp
		for (String s : JMudStatics.characters) {
			chars.put(s, new PlayerCharacter(s));
		}

		return chars;
	}

	public static Account getAccount(String uName) {
		// TODO Fix this STUB: MysqlConnection.getAccount(String uName)

		if (uName.equals(JMudStatics.hardcodeUName)) {
			return new Account(42, JMudStatics.hardcodeUName, JMudStatics.hardcodePasswd);
		} else {
			return null;
		}
	}

	public static PlayerCharacter getPlayerCharacter(String pcName, int ownerAccID) {
		// TODO Fix this STUB: MysqlConnection.getPlayerCharacter(String pcName)

		// Use ownerAccID in the SQL Query to verify ownership.
		if (ownerAccID != 42) {
			return null;
		}

		if (pcName.equals("Pinky")) {
			return new PlayerCharacter("Pinky");
		} else if (pcName.equals("Stinky")) {
			return new PlayerCharacter("Stinky");
		} else if (pcName.equals("Slinky")) {
			return new PlayerCharacter("Slinky");
		} else {
			return null;
		}
	}
}
