package jmud.engine.account;

import java.util.HashMap;
import java.util.HashSet;

/**
 * <code>PlayerAccountManager</code> is a singleton patterned class designed to
 * store and facilitate easy lookup of online <code>PlayerAccounts</code>.
 * @author david.h.loman
 */
public class PlayerAccountManager {

   /**
    * <code>PlayerAccountManagerHolder</code> is loaded on the first execution
    * of <code>PlayerAccountManager.getInstance()</code> or the first access to
    * <code>PlayerAccountManagerHolder.INSTANCE</code>, not before.
    */
   private static final class PlayerAccountManagerHolder {
      /**
       * The singleton instance.
       */
      private static final PlayerAccountManager INSTANCE = new PlayerAccountManager();

      /**
       * <code>PlayerAccountManagerHolder</code> is a utility class. Disallowing
       * public/default constructor.
       */
      private PlayerAccountManagerHolder() {

      }
   }

   /**
    * @return the singleton instance of the <code>PlayerAccountManager</code>
    */
   public static PlayerAccountManager getInstance() {
      return PlayerAccountManagerHolder.INSTANCE;
   }

   /**
    * A <code>HashSet</code> of <code>PlayerAccounts</code>.
    */
   private final HashSet<PlayerAccount> accountSet = new HashSet<PlayerAccount>();

   /**
    * A <code>HashMap</code> for e-mail addresses.
    */
   private final HashMap<String, PlayerAccount> emailMap = new HashMap<String, PlayerAccount>();
   /**
    * A <code>HashMap</code> for usernames.
    */
   private final HashMap<String, PlayerAccount> uNameMap = new HashMap<String, PlayerAccount>();

   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor.
    */
   protected PlayerAccountManager() {
   }

   /**
    * Adds a new <code>PlayerAccount</code>.
    * @param pa
    *           the account to add
    */
   public final void addPlayerAccount(final PlayerAccount pa) {
      this.accountSet.add(pa);
      this.uNameMap.put(pa.getUname(), pa);
      this.emailMap.put(pa.getEmailAddress(), pa);

   }

   /**
    * Get a player's account using the user's e-mail address as search criteria.
    * @param email
    *           the query criteria
    * @return a player's account as specified by <code>email</code>
    */
   public final PlayerAccount getPlayerAccountByEmail(final String email) {
      return this.emailMap.get(email);
   }

   /**
    * Get a player's account using the username as search criteria.
    * @param uname
    *           the query criteria
    * @return the player's account as specified by <code>uname</code>
    */
   public final PlayerAccount getPlayerAccountByUName(final String uname) {
      return this.uNameMap.get(uname);
   }

   /**
    * Remove a player's account.
    * @param pa
    *           the account to remove
    */
   public final void remPlayerAccount(final PlayerAccount pa) {
      this.accountSet.remove(pa);
      this.uNameMap.remove(pa.getUname());
      this.emailMap.remove(pa.getEmailAddress());
   }

}
