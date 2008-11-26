package jmud.engine.object;

/**
 * Thin wrapper around the hashmap.  This class is completely internally
 * synchronized.  No external synch needed.
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

public class JMudObjectMap {

	private HashMap<UUID, JMudObject> map = new HashMap<UUID, JMudObject>();

	public void clear() {
		synchronized (this.map) {
			this.map.clear();
		}
	}

	public Object clone() {
		synchronized (this.map) {
			return this.map.clone();
		}
	}

	public boolean containsKey(UUID arg0) {
		synchronized (this.map) {
			return this.map.containsKey(arg0);
		}
	}

	public boolean containsValue(JMudObject arg0) {
		synchronized (this.map) {
			return this.map.containsValue(arg0);
		}
	}

	public Set<Entry<UUID, JMudObject>> entrySet() {
		synchronized (this.map) {
			return this.map.entrySet();
		}
	}

	public JMudObject get(UUID arg0) {
		synchronized (this.map) {
			return this.map.get(arg0);
		}
	}

	public int hashCode() {
		synchronized (this.map) {
			return this.map.hashCode();
		}
	}

	public boolean isEmpty() {
		synchronized (this.map) {
			return this.map.isEmpty();
		}
	}

	public Set<UUID> keySet() {
		synchronized (this.map) {
			return this.map.keySet();
		}
	}

	public JMudObject put(UUID arg0, JMudObject arg1) {
		synchronized (this.map) {
			return this.map.put(arg0, arg1);
		}
	}

	public void putAll(Map<? extends UUID, ? extends JMudObject> arg0) {
		this.map.putAll(arg0);
	}

	public JMudObject remove(UUID arg0) {
		synchronized (this.map) {
			return this.map.remove(arg0);
		}
	}

	public int size() {
		synchronized (this.map) {
			return this.map.size();
		}
	}

	public String toString() {
		synchronized (this.map) {
			return this.map.toString();
		}
	}

	public Collection<JMudObject> values() {
		synchronized (this.map) {
			return this.map.values();
		}
	}

}
