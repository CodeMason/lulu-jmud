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
	public static boolean useMysql = false;

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
			return DriverManager.getConnection(JMudStatics.dbUrl + JMudStatics.dbName, JMudStatics.dbUName,
					JMudStatics.dbPassWd);
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
	 * DB access method for SQL statements that do NOT return any data except
	 * the number of Rows Modified.
	 * 
	 * @param sql
	 *            A valid SQL Update
	 * @return a ResultSet based on the passed in SQL statement.
	 */
	public static int insertOrUpdate(java.sql.Connection c, String sql) {
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

}
