package jmud.engine.acccount;

import jmud.engine.character.Character;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * 24NOV08:  PlayerAccount should only represent the data that pertains to
 * Account only, aka, username, email and password plus any settings they
 * might have, aka Telnet settings.
 *
 * A playerAccount will point to many Characters.
 *
 * Created on 24NOV08
 */




public class PlayerAccount {
   private int playerAccountID = -1;

    // validation
    private String uname = "";
    private String password = "";
    private String emailAddress = "";

    private Set<Character> characters = new HashSet<Character>();



	public PlayerAccount(String emailAddress, String password, int playerAccountID, String uname) {
		super();
		this.emailAddress = emailAddress;
		this.password = password;
		this.playerAccountID = playerAccountID;
		this.uname = uname;
	}

	public int getPlayerAccountID() {
		return playerAccountID;
	}

	public String getUname() {
		return uname;
	}

	public String getPassword() {
		return password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public Set<Character> getCharacters() {
		return characters;
	}



}
