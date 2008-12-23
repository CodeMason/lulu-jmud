package jmud.engine.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
	private static class LazyLoader {
		private static final CommandFactory LAZY_LOADED_INSTANCE = new CommandFactory();
		private LazyLoader() {
		}
	}

	public static CommandFactory getLazyLoadedInstance() {
		return LazyLoader.LAZY_LOADED_INSTANCE;
	}

	private final Map<String, AbstractCommand> cmdMap = Collections.synchronizedMap(new HashMap<String, AbstractCommand>());

	protected CommandFactory() {
        // Singleton
    }

	public final void addAbstractCommand(AbstractCommand cmd) {
		for (String alias : cmd.getAliases()) {
			this.cmdMap.put(alias, cmd);
		}
	}

	public final AbstractCommand getAbstractCommand(String alias) {
		return this.cmdMap.get(alias);
	}

	public final AbstractCommand remAbstractCommandAlias(String alias) {
		return this.cmdMap.remove(alias);
	}

	public final void remAbstractCommand(AbstractCommand cmd) {
        // ToDo CM: might want to remove based on the set of values in case the set of aliases has changed
        for (String alias : cmd.getAliases()) {
			this.cmdMap.remove(alias);
		}

	}

}
