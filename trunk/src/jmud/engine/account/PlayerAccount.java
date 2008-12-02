package jmud.engine.account;

import java.util.HashSet;
import java.util.Set;

import jmud.engine.character.Character;

/**
 * <code>PlayerAccount</code> represents the data that pertains to an account
 * only, such as the player's username, email and password plus any Telnet
 * settings a player might have. A <code>PlayerAccount</code> will point to many
 * Characters.
 * @author david.h.loman
 */
public class PlayerAccount {
   /**
    * The set of characters associated with this PlayerAccount.
    */
   private final Set<Character> characters = new HashSet<Character>();

   /**
    * Used for validation of the e-mail address.
    */
   private String emailAddress = "";
   /**
    * Used for validation of the password.
    */
   private String password = "";
   /**
    * the playerAccountID is initialized to a default value.
    */
   private int playerAccountID = -1;

   /**
    * Used for validation of the username.
    */
   private String uname = "";

   /**
    * Explicit constructor for the <code>PlayerAccount</code>.
    * @param inEmailAddress
    *           the supplied e-mail account
    * @param inPassword
    *           the supplied password
    * @param inPlayerAccountId
    *           the account ID
    * @param inUsername
    *           the supplied username
    */
   public PlayerAccount(final String inEmailAddress, final String inPassword,
         final int inPlayerAccountId, final String inUsername) {
      super();
      this.emailAddress = inEmailAddress;
      this.password = inPassword;
      this.playerAccountID = inPlayerAccountId;
      this.uname = inUsername;
   }

   /**
    * @return the set of <code>Characters</code> for this
    *         <code>PlayerAccount</code>
    */
   public final Set<Character> getCharacters() {
      return characters;
   }

   /**
    * @return the player's e-mail address
    */
   public final String getEmailAddress() {
      return emailAddress;
   }

   /**
    * @return the player's password
    */
   public final String getPassword() {
      return password;
   }

   /**
    * @return the account ID
    */
   public final int getPlayerAccountID() {
      return playerAccountID;
   }

   /**
    * @return the player's username
    */
   public final String getUname() {
      return uname;
   }

}
