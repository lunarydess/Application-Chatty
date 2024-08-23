package zip.luzey.chatty.server.command;

import zip.luzey.chatty.server.command.impl.CommandExit;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class CommandRegistry {
	private final CopyOnWriteArrayList<AbstractCommand> commands = new CopyOnWriteArrayList<>();

	public CommandRegistry() {
		this.register(
			 new CommandExit()
		);
	}

	public void register(final AbstractCommand... commands) {
		this.getCommands().addAll(List.of(commands));
	}

	public void execute(final String... arguments) {
		String name = arguments[0];
		String[] args = new String[arguments.length - 1];
		System.arraycopy(arguments, 1, args, 0, args.length);
		this.getCommands().stream()
		    .filter(command -> Arrays.stream(command.aliases())
		                             .filter(alias -> alias.equalsIgnoreCase(name))
		                             .findFirst()
		                             .orElse(null) != null)
		    .forEach(action -> action.run(args));
	}

	public CopyOnWriteArrayList<AbstractCommand> getCommands() {
		return this.commands;
	}
}
