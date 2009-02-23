package jmud.engine.dbio.util;

import java.sql.Connection;

import jmud.engine.dbio.MysqlConnection;

// ToDo CM: tables should be created based on the class structure if we're using a persistence technology (e.g. Hibernate)
public class MakeDBTables {

	/**
	 * Eventually, this will hold the routines that will generate the
	 * jmud DB and all the tables.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String sql;

		//Why doesn't this work... hrm...
		sql = " CREATE TABLE /*!32312 IF NOT EXISTS*/ 'accounts' (   'accountid' int(10) unsigned NOT NULL AUTO_INCREMENT,  'uname' varchar(255) NOT NULL,  'passwd' varchar(255) NOT NULL,   PRIMARY KEY ('accountid'),  UNIQUE KEY 'accountid' ('accountid'),   KEY 'accountid_2' ('accountid') ) AUTO_INCREMENT=2 COMMENT='This table holds the AccountID, uname, and passwords';";

		Connection con = MysqlConnection.makeNewConnection();
		int i = MysqlConnection.insertOrUpdate(con, sql);
		System.out.println(i);
	}

}
