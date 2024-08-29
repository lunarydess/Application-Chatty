package zip.luzey.chatty.server.command.impl;

import zip.luzey.chatty.server.ChattyServer;
import zip.luzey.chatty.server.command.AbstractCommand;

public final class CommandHelp extends AbstractCommand {
	private static final String[] ALIASES = {
		 "help",
		 "h",
		 "?"
	};

	@Override
	public String[] aliases() {
		return ALIASES;
	}

	@Override
	public boolean run(final String... args) {
		if (args.length > 1) return false;

		/*int pages = (int) (double) (ChattyServer.get().getCommandRegistry().getCommands().size() / 5);
		int page;
		try {
			page = Integer.parseInt(args[0]);
		} catch (final NumberFormatException ignored) {
			page = 1;
		}
		page = Math.max(1, Math.min(page, pages));*/

		return true;
	}

	@Override
	public String usage() {
		return "";
	}
}
