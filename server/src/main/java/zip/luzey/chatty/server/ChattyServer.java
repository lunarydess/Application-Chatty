package zip.luzey.chatty.server;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zip.luzey.chatty.server.command.CommandRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ChattyServer {
	private static ChattyServer server;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Terminal terminal = TerminalBuilder.builder()
	                                                 .system(true)
	                                                 .color(true)
	                                                 .encoding(StandardCharsets.UTF_8)
	                                                 .nativeSignals(true)
	                                                 .jna(true)
	                                                 .jni(true)
	                                                 .build();
	private final LineReader reader = LineReaderBuilder.builder()
	                                                   .terminal(terminal)
	                                                   .build();

	private final CommandRegistry commandRegistry = new CommandRegistry();
	private final Configurations configurations = new Configurations(throwable -> this.getLogger().error(throwable.getMessage(), throwable));
	private final CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();

	ChattyServer() throws IOException {}

	public static void main(final String... args) throws RuntimeException {
		try {
			server = new ChattyServer();
			if (!server.getConfigurations().load()) throw new ExceptionInInitializerError("config file couldn't be load");
			if (server.getConfigurations().getData() == null) throw new NullPointerException();
		} catch (IOException | RuntimeException e) {
			throw new RuntimeException(e);
		}

		new Thread(() -> {
			String line = server.getReader().readLine();
			do {
				server.getCommandRegistry().execute(line);
			} while ((line = server.getReader().readLine()) != null);
		}, "Input-Thread").start();

		try (final ServerSocket socketServer = new ServerSocket(server.getConfigurations().getData().general().port())) {
			server.getLogger().info(
				 """

				  ______  __            __    __         \s
				 |      ||  |--..---.-.|  |_ |  |_ .--.--.
				 |   ---||     ||  _  ||   _||   _||  |  |
				 |______||__|__||___._||____||____||___  |
				                                   |_____|

				  \u25a1 Listening on \u25e6 {}:{}
				  \u25a1 Made by \u25e6 lunarydess \u2661
				 \s""",
				 server.getConfigurations().getData().general().host(),
				 server.getConfigurations().getData().general().port()
			);
			for ( ; ; ) {
				server.getUsers().add(new User(socketServer.accept()));
			}
		} catch (IOException exception) {
			server.getLogger().error("couldn't accept server socket", exception);
		}
	}

	public static ChattyServer get() {
		return server;
	}

	public void shutdown() {
		System.exit(0);
	}

	public Logger getLogger() {
		return this.logger;
	}

	public Terminal getTerminal() {
		return this.terminal;
	}

	public LineReader getReader() {
		return this.reader;
	}

	public CommandRegistry getCommandRegistry() {
		return this.commandRegistry;
	}

	public Configurations getConfigurations() {
		return this.configurations;
	}

	public CopyOnWriteArrayList<User> getUsers() {
		return this.users;
	}
}
