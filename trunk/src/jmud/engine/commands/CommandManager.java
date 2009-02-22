package jmud.engine.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
	private static class LazyLoader {
		private static final CommandManager LAZY_LOADED_INSTANCE = new CommandManager();
		private LazyLoader() {
		}
	}

	public static CommandManager getLazyLoadedInstance() {
		return LazyLoader.LAZY_LOADED_INSTANCE;
	}

	private final Map<String, AbstractCommand> cmdMap = Collections.synchronizedMap(new HashMap<String, AbstractCommand>());

	protected CommandManager() {
        // Singleton
    }

	
	
	public final void registerCommand(AbstractCommand cmd) {
		for (String alias : cmd.getAliases()) {
			this.cmdMap.put(alias, cmd);
		}
	}

	public final AbstractCommand lookupCommand(String alias) {
		return this.cmdMap.get(alias);
	}


}
