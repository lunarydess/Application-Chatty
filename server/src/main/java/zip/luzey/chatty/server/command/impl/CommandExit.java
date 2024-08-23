package zip.luzey.chatty.server.command.impl;

import zip.luzey.chatty.server.ChattyServer;
import zip.luzey.chatty.server.command.AbstractCommand;

public final class CommandExit extends AbstractCommand {
	private static final String[] ALIASES = {
		 "exit",
		 "shutdown",
		 "stop",
		 "close",
		 "bye"
	};

	@Override
	public String[] aliases() {
		return ALIASES;
	}

	@Override
	public boolean run(final String... args) {
		ChattyServer.get().shutdown();
		return true;
	}

	@Override public String usage() {
		return "";
	}
}
