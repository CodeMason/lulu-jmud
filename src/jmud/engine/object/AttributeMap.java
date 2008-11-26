package jmud.engine.object;

/**
 * Thin wrapper around the hashmap.  This class is completely internally
 * synchronized.  No external synch needed.
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class AttributeMap {

	private HashMap<String, Attribute> map = new HashMap<String, Attribute>();

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

	public boolean containsKey(Object arg0) {
		synchronized (this.map) {
			return this.map.containsKey(arg0);
		}
	}

	public boolean containsValue(Object arg0) {
		synchronized (this.map) {
			return this.map.containsValue(arg0);
		}
	}

	public Set<Entry<String, Attribute>> entrySet() {
		synchronized (this.map) {
			return this.map.entrySet();
		}
	}

	public boolean equals(Object arg0) {
		synchronized (this.map) {
			return this.map.equals(arg0);
		}
	}

	public Attribute get(Object arg0) {
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

	public Set<String> keySet() {
		synchronized (this.map) {
			return this.map.keySet();
		}
	}

	public Attribute put(String arg0, Attribute arg1) {
		synchronized (this.map) {
			return this.map.put(arg0, arg1);
		}
	}

	public void putAll(Map<? extends String, ? extends Attribute> arg0) {
		this.map.putAll(arg0);
	}

	public Attribute remove(Object arg0) {
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

	public Collection<Attribute> values() {
		synchronized (this.map) {
			return this.map.values();
		}
	}

}
