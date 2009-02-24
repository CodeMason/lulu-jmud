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
package jmud.engine.dbio;

import jmud.engine.object.JMudObject;

import java.util.*;

public class FakeDatabaseObjectGetter implements DatabaseObjectGetter{
    private static final List<String> ROOMS = Arrays.asList("Room 1", "Room 2", "Room3");
    private static final List<String> EXITS = Arrays.asList("Room 1 east", "Room 2 west", "Room 2 east", "Room 3 west");
    private static final List<String> WEAPONS = Arrays.asList("Sword", "Bow", "Mace");
    private static final List<String> MOBS = Arrays.asList("Dragon", "Orc", "Goblin");
    private static final List<String> FOODS = Arrays.asList("Apple", "Bread", "Chicken");

    private Map<String, UUID> jmudObjectUUIDsByName;
    private Map<UUID, JMudObject> jmudObjectsByUUID;

    public FakeDatabaseObjectGetter(){
        createObjectMaps();
        createJMudObjectsByKind();
    }

    private void createObjectMaps(){
        jmudObjectUUIDsByName = new HashMap<String, UUID>();
        jmudObjectsByUUID = new HashMap<UUID, JMudObject>();
    }

    private void createJMudObjectsByKind(){
        createObjects(ROOMS);
        createObjects(EXITS);
        createObjects(WEAPONS);
        createObjects(MOBS);
        createObjects(FOODS);
    }

    private void createObjects(List<String> objectNames){
        JMudObject jmudObject;

        for(String objectName : objectNames){
            jmudObject = new JMudObject();
            jmudObject.setDisplayedName(objectName);
            jmudObjectUUIDsByName.put(objectName, jmudObject.getUUID());
            jmudObjectsByUUID.put(jmudObject.getUUID(), jmudObject);
        }
    }

    public JMudObject getObject(UUID objectUUID) {
        return jmudObjectsByUUID.get(objectUUID);
    }

    public JMudObject getObject(String objectName){
        UUID objectUUID = jmudObjectUUIDsByName.get(objectName);
        return jmudObjectsByUUID.get(objectUUID);
    }

    public List<String> getRoomNames(){
        return new ArrayList<String>(ROOMS);
    }

    public List<String> getExitNames(){
        return new ArrayList<String>(EXITS);
    }

    public List<String> getWeaponNames(){
        return new ArrayList<String>(WEAPONS);
    }

    public List<String> getMobNames(){
        return new ArrayList<String>(MOBS);
    }

    public List<String> getFoodNames(){
        return new ArrayList<String>(FOODS);
    }
}
