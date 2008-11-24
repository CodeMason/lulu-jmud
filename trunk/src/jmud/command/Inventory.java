/*
 * Inventory.java
 *
 * Created on December 13, 2007, 5:09 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jmud.command;

import jmud.core.Player;
import jmud.item.Item;
import jmud.netIO.deprecated.PlayerChannel;
import jmud.rooms.Room;
import jmud.slot.Slot;

/**
 * @author Chris Maguire
 */
public class Inventory extends Command {
    private PlayerChannel playerChannel;
    private Player player;
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
        for(Slot slot : player.getSlots()) {

            // DEBUG:
            //System.out.println("Number of items in slot: " + slot.getItems().size());

            for(Item item : slot.getItems()) {
                // DEBUG:
                //System.out.println("Inventory.exec() - item is " + (item == null ? "null" : "not null"));

                if(item != null) {
                    if(firstItem) {
                        firstItem = false;
                    } else {
                        msg.append(", ");
                    }
                    msg.append(item.getName());
                }
            }
        }
        // tack on the newline
        msg.append(CRLF).append(room.getExits()).append(CRLF).append(player.getPrompt());

        // now send back what we found to the player
        try {
            playerChannel.sendMessage(msg.toString());
        } catch(Exception e) {
            System.out.println("Couldn't print inventory for " + player.getName());
        }

        return true;
    }

}
