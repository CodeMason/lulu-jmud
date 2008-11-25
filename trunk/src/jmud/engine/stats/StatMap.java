package jmud.engine.stats;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class StatMap {
	private HashMap<String, Stat> stats = new HashMap<String, Stat>();

	public void clear() {
		stats.clear();
	}

	public Object clone() {
		return stats.clone();
	}

	public boolean containsKey(Object arg0) {
		return stats.containsKey(arg0);
	}

	public boolean containsValue(Object arg0) {
		return stats.containsValue(arg0);
	}

	public Set<Entry<String, Stat>> entrySet() {
		return stats.entrySet();
	}

	public boolean equals(Object arg0) {
		return stats.equals(arg0);
	}

	public Stat get(Object arg0) {
		return stats.get(arg0);
	}

	public int hashCode() {
		return stats.hashCode();
	}

	public boolean isEmpty() {
		return stats.isEmpty();
	}

	public Set<String> keySet() {
		return stats.keySet();
	}

	public Stat put(String arg0, Stat arg1) {
		return stats.put(arg0, arg1);
	}

	public void putAll(Map<? extends String, ? extends Stat> arg0) {
		stats.putAll(arg0);
	}

	public Stat remove(Object arg0) {
		return stats.remove(arg0);
	}

	public int size() {
		return stats.size();
	}

	public String toString() {
		return stats.toString();
	}

	public Collection<Stat> values() {
		return stats.values();
	}

	
	
}
