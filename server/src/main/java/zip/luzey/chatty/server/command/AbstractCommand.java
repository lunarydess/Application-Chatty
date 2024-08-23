package zip.luzey.chatty.server.command;

public abstract class AbstractCommand {
	public abstract String[] aliases();
	public abstract boolean run(final String... args);
	public abstract String usage();
}
