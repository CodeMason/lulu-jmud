package jmud.engine.account;

import java.util.HashMap;
import jmud.engine.dbio.util.SqlConnHelpers;

/**
 * <code>AccountManager</code> is a singleton patterned class designed to store
 * and facilitate easy lookup of online accounts.
 * 
 * @author david.h.loman
 */
public class AccountManager {
	/**
	 * <code>Holder</code> is loaded on the first execution of
	 * <code>AccountManager.getInstance()</code> or the first access to
	 * <code>Holder.INSTANCE</code>, not before.
	 */
	private static final class Holder {
		/**
		 * The singleton instance.
		 */
		private static final AccountManager INSTANCE = new AccountManager();

		/**
		 * <code>Holder</code> is a utility class. Disallowing public/default
		 * constructor.
		 */
		private Holder() {

		}
	}

	/**
	 * @return the singleton instance
	 */
	public static AccountManager getInstance() {
		return Holder.INSTANCE;
	}

	private final HashMap<Integer, Account> idMap = new HashMap<Integer, Account>();

	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor.
	 */
	protected AccountManager() {
	}

	/**
	 * Load an account.
	 * 
	 * @param uName
	 *            the username of the account to lookup
	 * 
	 * @return Either a reference to an Account object if the DB lookup is a
	 *         success, or NULL if the DB lookup fails.
	 */
	public Account loadAccount(String uName) {
		// Get the account data from the database
		Account a = SqlConnHelpers.getAccount(uName);

		if (a == null) {
			return null;
		} else {
			this.idMap.put(a.getAccountID(), a);
			return a;
		}
	}

	/**
	 * Unload an account.
	 * 
	 * @param acc
	 *            Account object reference to be unloaded
	 * 
	 * @return boolean value showing whether or not the Account object was
	 *         unloaded from the AccountManager successfully.
	 */
	public boolean unloadAccont(Account acc) {
		return this.unloadAccount(acc.getAccountID());
	}

	/**
	 * Unload an account.
	 * 
	 * @param accountID
	 *            Integer value representing the ID of the Account object to be
	 *            unloaded.
	 * 
	 * @return boolean value showing whether or not the Account object was
	 *         unloaded from the AccountManager successfully.
	 */
	public boolean unloadAccount(int accountID) {
		Account acc = this.idMap.remove(accountID);
		if (acc == null) {
			System.err.println("Failed to obtain Account object reference during unloadAccount()");
			return true;
		}
		if (acc.save() == false) {
			System.err.println("Account failed to .save() during unloadAccount()!!!!!");
			return true;
		}

		return true;
	}

	/**
	 * Load an account.
	 * 
	 * @param accountID
	 *            Integer value representing the ID of the Account object to be returned.
	 * 
	 * @return Either a reference to an Account object if the AccountManager lookup is a
	 *         success, or NULL if the AccountManager lookup fails.
	 */
	public Account getAccount(int accountID) {
		return this.idMap.get(accountID);
	}
	
}
