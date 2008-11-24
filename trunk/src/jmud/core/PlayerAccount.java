package jmud.core;

/**
 * 
 * 24NOV08:  PlayerAccount should only represent the data that pertains to
 * Account only, aka, username, email and password plus any settings they
 * might have, aka Telnet settings.
 * 
 * A playerAccount will point to many Characters.
 * 
 * 
 * 
 * A role playing personna to be controlled and used to interact with the game;
 * this class has a lot of the game code for controlling player state in response
 * to events like taking damage, poison, etc. We'll probably want to move this
 * into separate classes, e.g. DamageEvent, or DamageHandler
 *
 * Created on April 21, 2002, 4:24 PM
 */

import jmud.item.Item;
import jmud.rooms.Room;
import jmud.slot.Slot;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;




public class PlayerAccount {
   private int playerAccountID = -1;

    // validation
    private String login = "";
    private String password = "";
    private String emailAddress = "";

}
