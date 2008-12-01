package jmud.engine.account;

import java.util.HashMap;
import java.util.HashSet;

/**
 * PlayerAccountManager is a Singleton patterned class designed to store and
 * facilitate easy lookup of Online PlayerAccounts.
 */

public class PlayerAccountManager {
   /*
    * Singleton Implementation
    */

   /**
    * PlayerAccountManagerHolder is loaded on the first execution of
    * PlayerAccountManager.getInstance() or the first access to
    * PlayerAccountManagerHolder.INSTANCE, not before.
    */
   private static class PlayerAccountManagerHolder {
      private static final PlayerAccountManager INSTANCE = new PlayerAccountManager();
   }

   public static PlayerAccountManager getInstance() {
      return PlayerAccountManagerHolder.INSTANCE;
   }

   private final HashSet<PlayerAccount> accountSet = new HashSet<PlayerAccount>();

   /*
    * Concrete Class Implementation
    */

   private final HashMap<String, PlayerAccount> uNameMap = new HashMap<String, PlayerAccount>();
   private final HashMap<String, PlayerAccount> emailMap = new HashMap<String, PlayerAccount>();

   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor
    */
   protected PlayerAccountManager() {
   }

   public final void addPlayerAccount(final PlayerAccount pa) {
      this.accountSet.add(pa);
      this.uNameMap.put(pa.getUname(), pa);
      this.emailMap.put(pa.getEmailAddress(), pa);

   }

   public final PlayerAccount getPlayerAccountByEmail(final String email) {
      return this.emailMap.get(email);
   }

   public final PlayerAccount getPlayerAccountByUName(final String uname) {
      return this.uNameMap.get(uname);
   }

   public final void remPlayerAccount(final PlayerAccount pa) {
      this.accountSet.remove(pa);
      this.uNameMap.remove(pa.getUname());
      this.emailMap.remove(pa.getEmailAddress());
   }

}
