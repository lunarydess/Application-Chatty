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

	/**
	 * public void onCommand(String input, String[] args) throws Exception {
	 * <p>
	 * if (args.length == 0) {
	 * this.onCommand(input, new String[] { "1" });
	 * return;
	 * }
	 * if (args.length == 1) {
	 * if (!MiscUtils.isInteger(args[0])) {
	 * try {
	 * Command command = Hash.INSTANCE.getCommandManager().getCommand(args[0]);
	 * getChat().sendMessage("");
	 * getChat().sendMessage("§7Command: §3" + command.getCommand());
	 * getChat().sendMessage("§7Beschreibung:");
	 * for (String descrString : command.getCommandHelp()) {
	 * getChat().sendMessage("§7" + descrString);
	 * }
	 * if (command.getSyntax() != null) {
	 * getChat().sendMessage("");
	 * getChat().throwSyntax(command);
	 * return;
	 * }
	 * } catch (Exception e) {
	 * getChat().sendMessage("§7Der Befehl existiert nicht!");
	 * }
	 * return;
	 * }
	 * <p>
	 * int pages = (int) Math.ceil(Hash.INSTANCE.getCommandManager().getCommands().size() / 5D);
	 * int page = Integer.parseInt(args[0]);
	 * if (page > pages || page < 1) {
	 * getChat().sendMessage("Ungültige Seite (" + page + ")");
	 * return;
	 * }
	 * getChat().sendMessage("Verfügbare Befehle:");
	 * Iterator<Command> itr = Hash.INSTANCE.getCommandManager().getCommands().iterator();
	 * for (int i = 0; itr.hasNext(); i++) {
	 * Command cmd = itr.next();
	 * if (i >= (page - 1) * 5 && i < (page - 1) * 5 + 5)
	 * getChat().sendMessage("§3#" + cmd.getCommand());
	 * }
	 * getChat().sendMessage("Seite " + page + " / " + pages);
	 * <p>
	 * return;
	 * }
	 * }
	 */

	@Override
	public boolean run(final String... args) {
		if (args.length > 1) return false;

		int pages = (int) (double) (ChattyServer.get().getCommandRegistry().getCommands().size() / 5);
		int page;
		try {
			page = Integer.parseInt(args[0]);
		} catch (final NumberFormatException ignored) {
			page = 1;
		}
		page = Math.max(1, Math.min(page, pages));

		return true;
	}

	@Override
	public String usage() {
		return "";
	}
}
