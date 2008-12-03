package jmud.engine.mobs;

import jmud.engine.core.Targetable;
import jmud.engine.stats.StatMap;

/**
 * Represents a mobile entity in the MUD. Created on April 28, 2002, 8:56 AM
 */
public class Mob implements Targetable {

   private int iID;
   private MobType mobType;
   /**
    * Hitpoints.
    */
   private int iHP = 1;
   private int iMaxHP = 1;
   /**
    * Armour class.
    */
   private int iAC;
   /**
    * Strength.
    */
   private int iStrength = 1;
   /**
    * Dexterity.
    */
   private int iDexterity = 1;
   private boolean bAlive = true;

   /**
    * Creates new Mob.
    */
   public Mob() {
   }

   /**
    * Creates new Mob with hitpoints, armour class, strength and dexterity.
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
    * Compares two Mobs for equality.
    * @param o
    *           the Object to compare for equality
    */
   @Override
   public final boolean equals(final Object o) {
      return o.getClass() == this.getClass() && ((Mob) o).iID == this.iID;
   }

   /**
    * @return the mob's armour class.
    */
   public final int getAC() {
      return iAC;
   }

   /**
    * @return the mob's dexterity
    */
   public final int getDex() {
      return iDexterity;
   }

   /**
    * @return the Mob's remaining hit points
    */
   public final int getHP() {
      return iHP;
   }

   /**
    * @return the mob's ID
    */
   public final int getID() {
      return iID;
   }

   /**
    * Get the <code>MobType</code> for this Mob.
    * @return this <code>Mob</code>'s <code>MobType</code> even if null
    */
   public final MobType getMobType() {
      return mobType;
   }

   /**
    * Get the name of this <code>Mob</code>'s <code>MobType</code>. <p/>
    * Required by <code>Target</code> interface.
    * @return the <code>MobType</code> name for this <code>Mob</code>
    */
   public final String getName() {
      return mobType.getType();
   }

   /**
    * @see jmud.engine.core.Targetable#getStatMap()
    * @return null for now
    */
   @Override
   public final StatMap getStatMap() {
      return null;
   }

   /**
    * @return the mob's strength.
    */
   public final int getStr() {
      return iStrength;
   }

   /**
    * @return a unique number for every mob
    */
   @Override
   public final int hashCode() {
      return iID;
   }

   /**
    * Subtracts hitpoints from the mob.
    * @param iDmg
    *           the amount of damage incurred
    * @return true if hitpoints <= 0
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
    * @return true if the Mob is alive
    */
   public final boolean isAlive() {
      return bAlive;
   }

}
