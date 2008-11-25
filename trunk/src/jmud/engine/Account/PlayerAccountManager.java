package jmud.engine.Account;

import java.util.HashMap;
import java.util.HashSet;

/**
 * PlayerAccountManager is a Singleton patterned class designed to store and
 * facilitate easy lookup of Online PlayerAccounts.
 *
 */

public class PlayerAccountManager {
	/* 
	 * ********************************************
	 * Singleton Implementation
	 * ********************************************
	 */	
	
	
	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected PlayerAccountManager() {
	}

	/**
	 * PlayerAccountManagerHolder is loaded on the first execution of
	 * PlayerAccountManager.getInstance() or the first access to
	 * PlayerAccountManagerHolder.INSTANCE, not before.
	 */
	private static class PlayerAccountManagerHolder {
		private final static PlayerAccountManager INSTANCE = new PlayerAccountManager();
	}

	public static PlayerAccountManager getInstance() {
		return PlayerAccountManagerHolder.INSTANCE;
	}

	/* 
	 * ********************************************
	 * Concrete Class Implementation
	 * ********************************************
	 */	
	
	
	private HashSet<PlayerAccount> accountSet = new HashSet<PlayerAccount>();
	private HashMap<String, PlayerAccount> uNameMap = new HashMap<String, PlayerAccount>();
	private HashMap<String, PlayerAccount> emailMap = new HashMap<String, PlayerAccount>();
	
	public void addPlayerAccount(PlayerAccount pa) {
		this.accountSet.add(pa);
		this.uNameMap.put(pa.getUname(), pa);
		this.emailMap.put(pa.getEmailAddress(), pa);
		
	}
	
	public void remPlayerAccount(PlayerAccount pa) {
		this.accountSet.remove(pa);
		this.uNameMap.remove(pa.getUname());
		this.emailMap.remove(pa.getEmailAddress());
	}
	
	public PlayerAccount getPlayerAccountByEmail(String email) {
		return this.emailMap.get(email);
	}
	
	public PlayerAccount getPlayerAccountByUName(String uname) {
		return this.uNameMap.get(uname);
	}
	
}













