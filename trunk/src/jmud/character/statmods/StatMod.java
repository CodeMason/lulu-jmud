package jmud.character.statmods;

public class StatMod {
	private String nameOfMod = "";
	private String nameOfStatToMod = "";
	
	private int modValue = 0;

	public StatMod(String nameOfMod, String nameOfStatToMod, int modValue) {
		super();
		this.modValue = modValue;
		this.nameOfMod = nameOfMod;
		this.nameOfStatToMod = nameOfStatToMod;
	}

	public String getNameOfMod() {
		return nameOfMod;
	}

	public String getNameOfStatToMod() {
		return nameOfStatToMod;
	}

	public int getModValue() {
		return modValue;
	}
	
	
	
}
