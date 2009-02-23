package jmud.engine.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import jmud.engine.netio.JMudClient;
import jmud.engine.dbio.Persistable;

public class Account implements Persistable {
	private int accountID;
	private String uName = "";
	private String passWd = "";
	private int loginAttempts;
	private boolean locked;

	private JMudClient c;

	public Account() {
		this(0);
	}

	public Account(int accountID) {
		this(accountID, "", "");
	}

	public Account(int accountID, String name, String passWd) {
		this(accountID, name, passWd, 0, null);
	}

	public Account(int accountID, String name, String passWd, int loginAttempts, JMudClient c) {
		super();
		this.accountID = accountID;
		this.passWd = passWd;
		this.uName = name;
		this.loginAttempts = loginAttempts;
		this.c = c;
	}

	public Account(ResultSet rs) throws SQLException {
		super();
		if (rs.first() == false) {
			throw new SQLException("Empty ResultSet.");
		}

		this.accountID = rs.getInt("accountid");
		this.uName = rs.getString("uname");
		this.passWd = rs.getString("passwd");
		// this.loginAttempts = 0;
		this.locked = rs.getBoolean("locked");
		
	}

	@Override
	public boolean save() {
		// TODO Finish Account.Save()
		return false;
	}


	/*
	 * Getters n Setters
	 */

	/**
	 * @return the AccountID associated with this Connection.
	 */
	public int getAccountID() {
		return this.accountID;
	}

	/**
	 * Set the AccountID associated with this Connection
	 * 
	 * @param accountID
	 */
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	/**
	 * @return the username associated with this Connection.
	 */
	public String getUName() {
		return this.uName;
	}

	/**
	 * Set the username associated with this Connection.
	 * 
	 * @param uName
	 */
	public void setUName(String uName) {
		this.uName = uName;
	}

	/**
	 * @return the password associated with this Connection.
	 */
	public String getPassWd() {
		return this.passWd;
	}

	/**
	 * Set the password associated with this Connection
	 * 
	 * @param passWd
	 */
	public void setPassWd(String passWd) {
		this.passWd = passWd;
	}

	/**
	 * @return the number of Login attempts for this connection.
	 */
	public int getLoginAttempts() {
		return this.loginAttempts;
	}

	/**
	 * Reset this Connection object's logAttempts;
	 */
	public void resetLoginAttempts() {
		this.loginAttempts = 0;
	}

	/**
	 * Increment this Connection object's logAttempts;
	 */
	public void incrementLoginAttempts() {
		++this.loginAttempts;
	}

	/**
	 * @return the Connection object reference associated with this Account.
	 */
	public JMudClient getC() {
		return c;
	}

	/**
	 * @param c
	 *            the Connection object reference to associate with this
	 *            Account.
	 */
	public void setC(JMudClient c) {
		this.c = c;
	}

	
	
	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
