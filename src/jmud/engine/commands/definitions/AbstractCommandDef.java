package jmud.engine.commands.definitions;

import java.util.ArrayList;

public abstract class AbstractCommandDef {

	public abstract boolean doCmd();


	protected String cmdName = "";
	protected ArrayList<String> aliases = new ArrayList<String>();



	public AbstractCommandDef(String cmdName) {
        this.cmdName = cmdName;
	}



	public String getCmdName() {
		return cmdName;
	}
	public ArrayList<String> getAliases() {
		return aliases;
	}




}
