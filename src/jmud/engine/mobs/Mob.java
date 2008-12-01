package jmud.engine.mobs;

import jmud.engine.core.Targetable;
import jmud.engine.stats.StatMap;

/**
 * Represents a mobile entity in the MUD. Created on April 28, 2002, 8:56 AM
 */
public class Mob implements Targetable {

   private int iID;
   private MobType mobType;
   private int iHP = 1;
   private int iMaxHP = 1;
   private int iAC;
   private int iStrength = 1;
   private int iDexterity = 1;
   private boolean bAlive = true;

   /**
    * Creates new Mob
    */
   public Mob() {
   }

   /**
    * Creates new Mob with hitpoints, armour class, strength and dexterity
    */
   public Mob(final int iID, final MobType mobType, final int iHP,
         final int iMaxHP, final int iAC, final int iStrength,
         final int iDexterity) {
      this.iID = iID;
      this.mobType = mobType;
      this.iHP = iHP;
      this.iMaxHP = iMaxHP;
      this.iAC = iAC;
      this.iStrength = iStrength;
      this.iDexterity = iDexterity;
   }

   /**
    * Compares two Mobs for equality
    */
   @Override
   public final boolean equals(final Object o) {
      return o.getClass() == this.getClass() && ((Mob) o).iID == this.iID;
   }

   /**
    * Returns the mob's Armour Class
    */
   public final int getAC() {
      return iAC;
   }

   /**
    * Returns the mob's Dexterity
    */
   public final int getDex() {
      return iDexterity;
   }

   /**
    * returns the Mob's remaing hit points
    */
   public final int getHP() {
      return iHP;
   }

   /**
    * returns the Mob's ID
    */
   public final int getID() {
      return iID;
   }

   /**
    * Get the <code>MobType</code> for this Mob
    * @return This <code>Mob</code>'s <code>MobType</code> even if null
    */
   public final MobType getMobType() {
      return mobType;
   }

   /**
    * Get the name of this <code>Mob</code>'s <code>MobType</code> <p/> Required
    * by Target interface
    * @return the <code>MobType</code> name for this <code>Mob</code>
    */
   public final String getName() {
      return mobType.getType();
   }

   @Override
   public final StatMap getStatMap() {
      return null;
   }

   /**
    * Returns the mob's Strength
    */
   public final int getStr() {
      return iStrength;
   }

   /**
    * returns a unique number for every mob
    */
   @Override
   public final int hashCode() {
      return iID;
   }

   /**
    * Subtracts hitpoints from the mob, returns true if hitpoints <= 0
    */
   public final boolean hurt(final int iDmg) {
      iHP -= iDmg;

      // if the Mob is dead then set the alive flag
      // bAlive = !(iHP > 0);
      // Scratch that: we'll complicate this and set it in the return statement
      // :) (yeah, yeah, so shoot me)

      // DEBUG:
      System.out.println(mobType.getType() + " has " + iHP + " of " + iMaxHP
            + " hitpoints.");

      // set and return the alive flag based on whether the Mob was killed
      return bAlive = !(iHP > 0);
   }

   /**
    * Returns true if the Mob is alive
    */
   public final boolean isAlive() {
      return bAlive;
   }

}
