/*
 * Inventory.java
 *
 * Created on December 13, 2007, 5:09 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jmud.testserver.commands;

import jmud.engine.character.Character;
import jmud.engine.core.Settings;
import jmud.engine.netIO.deprecated.PlayerChannel;
import jmud.engine.rooms.Room;

/**
 * @author Chris Maguire
 */
public class Inventory extends Command {
    private PlayerChannel playerChannel;
    private Character player;
    private Room room;

    /**
     * Creates a new instance of Inventory
     *
     * @param pc     the player channel that is executing this command
     * @param r      the room from which a player is executing this command
     * @param target any parameters included in the command
     */
    public Inventory(PlayerChannel pc, Room r, String target) {
        this.playerChannel = pc;
        this.player = pc.getPlayer();
        this.room = r;
    }

    public boolean exec() {
        boolean firstItem = true;
        StringBuilder msg = new StringBuilder("Items you have: ");

        // whip through each slot and add item descriptions

      //FIXME Had to stub this due to Massive Architecture rework.
//        for(Slot slot : player.getSlots()) {
//
//            // DEBUG:
//            //System.out.println("Number of items in slot: " + slot.getItems().size());
//
//            for(Item item : slot.getItems()) {
//                // DEBUG:
//                //System.out.println("Inventory.exec() - item is " + (item == null ? "null" : "not null"));
//
//                if(item != null) {
//                    if(firstItem) {
//                        firstItem = false;
//                    } else {
//                        msg.append(", ");
//                    }
//                    msg.append(item.getName());
//                }
//            }
//        }
        // tack on the newline
        msg.append(Settings.CRLF).append(room.getExits()).append(Settings.CRLF).append(player.getPrompt());

        // now send back what we found to the player
        try {
            playerChannel.sendMessage(msg.toString());
        } catch(Exception e) {
            System.out.println("Couldn't print inventory for " + player.getName());
        }

        return true;
    }

}