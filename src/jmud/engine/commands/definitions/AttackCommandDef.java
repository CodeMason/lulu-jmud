package jmud.engine.commands.definitions;

import jmud.engine.core.Target;

/**
 * Executable command attack: handles a player attacking something
 *
 * Created on 24Nov08
 *
 * ToDo Calculate the damage done to the target based on the player's stats and
 *      the targets armor class
 * ToDo Either use a random attack verb (e.g. "smashes", "impales") when a player
 *      hits a target or based the verb on the amount of damage.
 * ToDo Choose an attack verb that fits the players weapon (e.g. swords don't whip or crush)
 *
 */
public class AttackCommandDef extends AbstractCommandDef {

	private Character c;
	private Target t;

     public AttackCommandDef(Character c, Target t) {
    	 super("attack");

        this.aliases.add("att");
        this.aliases.add("atta");
        this.aliases.add("attac");
        this.aliases.add("attack");
    }

    /**
     * Executes an attack by the attacker on the attackee. Attack may not succeed if the
     * target specified in the constructor does not match any attackable targets
     * in the attacker's current room.
     * <br>
     * The attack will be repeated until:
     * <br>
     * - The specific target that is being attacked dies
     * <br>
     * <b>or</b>
     * <br>
     * - The specific target that is being attacked fleas
     * <br>
     * <b>or</b>
     * <br>
     * - The attacker leaves the room
     * <br>
     * <b>or</b>
     * <br>
     * - The attacker dies
     *
     * @return true if the attack completed or false if the attack needs to be run again.
     */
    public boolean doCmd() {
        int iDamage = 1;
        int iThac0 = 10;
        int iHitRoll;
        boolean bHit;

    	//TODO  I don't know how, exactly, we will make the attack rules abstract...



    	//Check to see if Target and Character are in Same Room:



    	//Check to see if Target isa Character (aka PK)




        /*****************************************************************************/
        /* AT THIS POINT WE HAVE A MOB                                               */

        // generate a roll (as in rolling the dice) and decide if they hit the target
        iHitRoll = (int) (Math.random() * 20); //TODO Abstract out the Dice rolling into a utility class.
        bHit = (iHitRoll >= (iThac0 + t.getAC()));

        // did the player miss the target?
        if(!bHit) {

            // broadcast a "MISSED!" message
            try {
                /*
                room.sendMessageToAll(""
                    + playerChannel.getPlayer().getName()
                    + " swings at "
                    + target.getName()
                    + " and misses!\n\r");
                */
                /* ToDo: We need a catalogue of miss verbs (for variety)
                    However, we may want to consider macros, this could screw up a macro, which might be good
                */
            } catch(Exception e) {
                System.out.println("Attack.exec(): room.sendMessage(\"Miss\") failed: \n\r"
                    + e.getMessage());
            }

            // else the player hit the target
        } else {

            // get the amount of damage
            // iDamage = pc.getPlayer().getDamage();
            // we need to calculate this using the players stats, stat modifiers and the
            // the targets armor class

            // broadcast a "HIT!" message
            try {
                // ToDo Again, we need to replace "smashes" with something player, weapon and--potentially--mob specific
               /* room.sendMessageToAll(""
                    + playerChannel.getPlayer().getName()
                    + " smashes "
                    + target.getName()
                    + " for "
                    + iDamage
                    + " damage!\n\r");*/
            } catch(Exception e) {
                System.out.println("Attack.exec(): ChannelWriter.sendMessage(\"Hit\") failed \n\r"
                    + e.getMessage());
            }
        }

        // do damage and check for killed IF they scored a hit
        if(bHit && t.hurt(iDamage)) {
            //mark the target as dead
            //Scratch that: the target should "kill" itself (i.e. target.hurt() should set the isAlive flag)

            // broadcast a "KILLED!" message
            // now this is retarded, but you can't rewind an iterator, have to get a new one
            try {
                // ToDo Again, we need to replace the boring "killed" with a variety of verbs, maybe depending on the
                // amount of excess damage, speed of the kill, number of mobs of this type already killed
                // ToDo Move this to its new home (don't know where that is yet)
                /*room.sendMessageToAll(""
                    + playerChannel.getPlayer().getName()
                    + " killed "
                    + target.getName()
                    + "!\n\r");*/
            } catch(Exception e) {
                System.out.println("Attack.exec(): ChannelWriter.sendMessage(\"Killed\") failed \n\r"
                    + e.getMessage());
            }

            // The enemy is dead, so tell the game engine that we're done
            return true;
        }

        // The target isn't dead yet so tell the game engine to run this command again
        return false;
    }
}