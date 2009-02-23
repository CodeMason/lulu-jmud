package jmud.engine.dbio.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jmud.engine.account.Account;
import jmud.engine.character.PlayerCharacter;
import jmud.engine.core.JMudStatics;
import jmud.engine.dbio.MysqlConnection;

public class SqlConnHelpers {

	/*                                      */
	/*                                      */
	/* Specific DataGet Methods */
	/*                                      */
	/*                                      */

	/**
	 * Takes the String UName of and attempts to look up an account. If an
	 * account is found in the DB, an Account object is returned. If the DB
	 * lookup fails, then null is returned.
	 */
	public static Account getAccount(String uName) {
		if (!MysqlConnection.useMysql) {
			// If we are using a Development DB stub.
			return new Account(42, JMudStatics.hardcodeUName, JMudStatics.hardcodePasswd);
		}

		try {
			java.sql.Connection c = MysqlConnection.makeNewConnection();

			String sql = "SELECT * FROM Accounts WHERE (uname='" + uName + "');";
			ResultSet rs = MysqlConnection.Query(c, sql);

			Account a;
			a = new Account(rs); // This might throw an SQLException if the
			// ResultSet is empty!
			rs.close();
			c.close();
			return a;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static boolean saveAccount(Account a) {
		if (!MysqlConnection.useMysql) {
			// If we are using a Development DB stub.
			return true;
		}

		try {
			java.sql.Connection c = MysqlConnection.makeNewConnection();
			String sql = "";

			// Check to see if the account is already there!
			if (SqlConnHelpers.getAccount(a.getPassWd()) == null) {
				// New account
				sql += "INSERT INTO accounts (";
				sql += "uname, passwd, locked";
				sql += ") VALUES (";
				sql += "'" + a.getUName() + "', ";
				sql += "'" + a.getPassWd() + "', ";
				sql += "" + a.isLocked() + "";
				sql += ")";
			} else {
				// existing account

				sql += "UPDATE accounts SET";
				sql += "uname='" + a.getUName() + "', ";
				sql += "passwd='" + a.getPassWd() + "', ";
				sql += "locked='" + a.isLocked() + "' ";
				sql += "WHERE accountid=" + a.getAccountID();

			}

			int rs = MysqlConnection.insertOrUpdate(c, sql);

			c.close();
			return (rs == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
