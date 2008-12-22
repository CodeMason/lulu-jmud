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
package jmud.engine.object;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JMudObjectManager {
	/**
	 * <code>Holder</code> is loaded on the first execution of
	 * <code>JMudObjectManager.getInstance()</code> or the first access to
	 * <code>Holder.INSTANCE</code>, not before.
	 */
	private static final class Holder {
		/**
		 * The singleton instance.
		 */
		private static final JMudObjectManager INSTANCE = new JMudObjectManager();

		/**
		 * <code>Holder</code> is a utility class. Disallowing public/default
		 * constructor.
		 */
		private Holder() {

		}
	}

	/**
	 * @return the singleton instance
	 */
	public static JMudObjectManager getInstance() {
		return Holder.INSTANCE;
	}

	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor.
	 */
	protected JMudObjectManager() {
	}

	private Map<UUID, JMudObject> uuidMap = null;

	public void init() {
		this.uuidMap = Collections.synchronizedMap(new HashMap<UUID, JMudObject>());

		// load the tree from the DB
		this.loadJMudObjectTreeFromDB();

	}

	private void loadJMudObjectTreeFromDB() {
		// TODO stubbed JMudObject load from DB here.

		//Get the JMudObject from the Database
		JMudObject jmo = null; //stubbed.

		//Look up the parent by UUID
		UUID parentID = jmo.getParentObject().getUuid();
		JMudObject parent = JMudObjectManager.getInstance().getJMudObject(parentID);

		//Attach
		parent.addChildObject(jmo);

		return;
	}

	public JMudObject getJMudObject(UUID uuid) {
		return uuidMap.get(uuid);
	}

}
