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
