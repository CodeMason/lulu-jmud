package jmud;
/*
 * Currently PlayerChannel contains all the requisite
 * info for a players in-game state AND all info required
 * for sending and receiving data from the client.
 * The List<PlayerChannel> is now maintained in a
 * Thread subclass that is also of Stereotype: Control.  
 * 
 * To improve thread safety and data accessibility,
 * Move PlayerChannel lists/maps to a 
 * Singleton Class (PlayerManager)
 * anchored to the root executable class and conform this
 * class to the Stereotype: Entity as much as practical. 
 *
 */

public class PlayerManager {

}
