package jmud.engine.account;

import jmud.engine.netIO.Connection;
import jmud.engine.dbio.Persistable;

public class Account implements Persistable {
	private int accountID = 0;
	private String uName = "";
	private String passWd = "";
	private int loginAttempts = 0;

	private Connection c;

	public Account() {
		this(0);
	}

	public Account(int accountID) {
		this(accountID, "", "");
	}

	public Account(int accountID, String name, String passWd) {
		this(accountID, name, passWd, 0, null);
	}

	public Account(int accountID, String name, String passWd, int loginAttempts, Connection c) {
		super();
		this.accountID = accountID;
		this.passWd = passWd;
		this.uName = name;
		this.loginAttempts = loginAttempts;
		this.c = c;
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
	public Connection getC() {
		return c;
	}

	/**
	 * @param c
	 *            the Connection object reference to associate with this
	 *            Account.
	 */
	public void setC(Connection c) {
		this.c = c;
	}

	@Override
	public boolean save() {
		// TODO Finish Account.Save()
		return false;
	}

}
