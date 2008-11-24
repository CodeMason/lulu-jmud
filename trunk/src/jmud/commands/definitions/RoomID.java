package jmud.commands.definitions;

import jmud.character.Character;
import jmud.core.Settings;
import jmud.netIO.deprecated.PlayerChannel;
import jmud.rooms.Room;

import java.nio.channels.SocketChannel;

public class RoomID extends Command {
    private PlayerChannel playerChannel;
    private Character player;
    private SocketChannel sc;
    private Room room;

    /**
     * Construct a down command
     *
     * @param pc       The PlayerChannel to move down
     * @param r        The current room
     * @param whatever Not used, but all command constructors must have all three parameters
     */
    public RoomID(PlayerChannel pc, Room r, String whatever) {
        this.playerChannel = pc;
        this.player = pc.getPlayer();
        this.room = r;
    }

    /**
     * Each subclass of Command must implement its own exec() method because
     * that's what commands are for, executing specific MUD commands.
     */
    public boolean exec() {
        // Tell the player what else is in the room they just entered
        try {
            playerChannel.sendMessage(new StringBuilder().append(room.getID())
                .append(Settings.CRLF)
                .append(player.getPrompt()).toString());

            // send the message that the player left to the room they just left
            room.up.sendMessageToAll(Settings.CRLF
                + player.getName()
                + " went down"
                + Settings.CRLF);
        } catch(Exception e) {
            System.out.println("sending room ID message failed:\n\r"
                + e.getMessage());
        }

        // Return true for "Yes, this command is finished"
        return true;
    }
}
