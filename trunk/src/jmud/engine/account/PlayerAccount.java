package jmud.engine.account;

import java.util.HashSet;
import java.util.Set;

import jmud.engine.character.Character;

/**
 * 24NOV08: PlayerAccount should only represent the data that pertains to
 * Account only, aka, username, email and password plus any settings they might
 * have, aka Telnet settings. A playerAccount will point to many Characters.
 * Created on 24NOV08
 */

public class PlayerAccount {
   private int playerAccountID = -1;

   // validation
   private String uname = "";
   private String password = "";
   private String emailAddress = "";

   private final Set<Character> characters = new HashSet<Character>();

   public PlayerAccount(final String emailAddress, final String password,
         final int playerAccountID, final String uname) {
      super();
      this.emailAddress = emailAddress;
      this.password = password;
      this.playerAccountID = playerAccountID;
      this.uname = uname;
   }

   public final Set<Character> getCharacters() {
      return characters;
   }

   public final String getEmailAddress() {
      return emailAddress;
   }

   public final String getPassword() {
      return password;
   }

   public final int getPlayerAccountID() {
      return playerAccountID;
   }

   public final String getUname() {
      return uname;
   }

}
