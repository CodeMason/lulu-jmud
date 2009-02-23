package jmud.engine.dbio;

import jmud.engine.account.Account;
import jmud.engine.character.PlayerCharacter;
import jmud.engine.core.JMudStatics;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides access to the database and functions to return specific data from
 * the database.
 * 
 * @author David Loman
 */

public class MysqlConnection {
	private static boolean useMysql = false;

	// TODO Commented out the whole class since its doing nothing but generating
	// compile errors right now.
	// FIXME Rework the Database IO layer after the basic mud data structs are
	// in place.
	/**
	 * Use values from JMudStatics and create a DB connection.
	 */
	public static java.sql.Connection makeNewConnection() {

		/*
		 * use the default constructor for the jdbc driver class to create a new
		 * instance to obtain a connection object the driver package com
		 * directory must be in the same directory as the jmud source
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection(JMudStatics.dbUrl,
					JMudStatics.dbUName, JMudStatics.dbPassWd);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DB access method for SQL statements that will return a ResultSet.
	 * 
	 * @param sql
	 *            A valid SQL Query
	 * @return a ResultSet based on the passed in SQL statement.
	 */
	public static ResultSet Query(java.sql.Connection c, String sql) {
		Statement s;
		ResultSet rs;

		try {
			s = c.createStatement();
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
	 * @param sql
	 *            A valid SQL Update
	 * @return a ResultSet based on the passed in SQL statement.
	 */
	public static int Update(java.sql.Connection c, String sql) {
		Statement s;
		int rs;

		try {
			s = c.createStatement();
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
	public static Account verifyLogin(String uname) {

		if (MysqlConnection.useMysql) {
			try {
				java.sql.Connection c = MysqlConnection.makeNewConnection();

				String sql = "SELECT * FROM Accounts WHERE (uname='" + uname
						+ "');";
				ResultSet rs = MysqlConnection.Query(c, sql);

				Account a;
				a = new Account(rs);
				rs.close();
				c.close();
				return a;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		} else {
			// If we are using a Development DB stub.
			return new Account(42, JMudStatics.hardcodeUName,
					JMudStatics.hardcodePasswd);
		}
	}

	public static Map<String, PlayerCharacter> getCharactersByAccountID(
			int accountID) {
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
			return new Account(42, JMudStatics.hardcodeUName,
					JMudStatics.hardcodePasswd);
		} else {
			return null;
		}
	}

	public static PlayerCharacter getPlayerCharacter(String pcName,
			int ownerAccID) {
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
