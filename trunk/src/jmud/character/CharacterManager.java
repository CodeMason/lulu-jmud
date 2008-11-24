package jmud.character;

import java.util.HashMap;

/**
 * CharacterManager is a Singleton patterned class designed to store and
 * facilitate easy lookup of Online Characters.
 *
 */

public class CharacterManager {
	/*
	 * ********************************************
	 * Singleton Implementation
	 * ********************************************
	 */


	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected CharacterManager() {
	}

	/**
	 * CharacterManagerHolder is loaded on the first execution of
	 * CharacterManager.getInstance() or the first access to
	 * CharacterManagerHolder.INSTANCE, not before.
	 */
	private static class CharacterManagerHolder {
		private static final CharacterManager INSTANCE = new CharacterManager();
	}

	public static CharacterManager getInstance() {
		return CharacterManagerHolder.INSTANCE;
	}

	/*
	 * ********************************************
	 * Concrete Class Implementation
	 * ********************************************
	 */

	private HashMap<String, Character> nameMap = new HashMap<String, Character>();

	public void addCharacter(Character c) {
		this.nameMap.put(c.getName(), c);

	}

	public void remCharacter(Character c) {
		this.nameMap.remove(c.getName());
	}


	public Character getCharacterByName(String name) {
		return this.nameMap.get(name);
	}

}













