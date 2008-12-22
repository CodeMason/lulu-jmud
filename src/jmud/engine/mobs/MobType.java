/**
 * This file is part of Lulu's JMud.
 *
 *  Lulu's JMud is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Lulu's JMud is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Lulu's JMud.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmud.engine.mobs;

/**
 * A type of Mob (Mobile Object) that specifies the type, acronym and
 * description of the mob. Created on April 28, 2002, 8:56 AM
 */
public class MobType {

   private String strType;
   private String strAcronym;
   private String strDesc;

   /**
    * Creates new Mob.
    */
   public MobType() {
   }

   /**
    * Creates new Mob with type, acronym and description
    * @param type
    *           The name of this Mob type
    * @param acronym
    *           The "nickname" of this Mob type
    * @param desc
    *           The description of this Mob type
    */
   public MobType(final String type, final String acronym, final String desc) {
      this.strType = type;
      this.strAcronym = acronym;
      this.strDesc = desc;
   }

   /**
    * Gets the acronym for this <code>MobType</code>
    * @return The acronym for this Mob Type (or group, class, species, etc)
    */
   public final String getAcronym() {
      return strAcronym;
   }

   /**
    * Gets the description for this type of Mob
    * @return The Mob type description
    */
   public final String getDesc() {
      return strDesc;
   }

   /**
    * Gets the type name of this Mob type
    * @return The actual name of this Mob Type (or group, class, species, etc)
    */
   public final String getType() {
      return strType;
   }

   /**
    * Sets the type name of this Mob type
    * @param strAcronym
    *           The new acronym for this <code>MobType</code>
    */
   public final void setAcronym(final String strAcronym) {
      this.strAcronym = strAcronym;
   }

   /**
    * Sets the description for this type of Mob
    * @param strDesc
    *           The new description (sensory perception) of this Mob type
    */
   public final void setDesc(final String strDesc) {
      this.strDesc = strDesc;
   }

   /**
    * Sets the type name of this Mob type
    * @param strType
    *           The new type name for this type
    */
   public final void setType(final String strType) {
      this.strType = strType;
   }
}
