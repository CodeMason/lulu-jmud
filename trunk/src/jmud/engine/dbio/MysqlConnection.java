package jmud.engine.dbio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	/*      Generic Utility Methods         */
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

}
