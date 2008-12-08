package jmud.test.dbio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import jmud.engine.dbio.MysqlConnection;

public class DBConnectionTesting {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {

		String sql;

		sql = "SELECT * FROM accounts";
		
		Connection con = MysqlConnection.makeNewConnection();
		ResultSet rs = MysqlConnection.Query(con, sql);
		System.out.println(rs);
		
		while (rs.next()) {
			System.out.println(rs.getString("uname") + " " + rs.getString("passwd"));
		}
		
	}

}
