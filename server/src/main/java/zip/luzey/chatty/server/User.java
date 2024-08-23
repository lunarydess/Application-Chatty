package zip.luzey.chatty.server;

import zip.luzey.chatty.server.util.UUIDCache;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public final class User {
	private static final UUID UUID_EMPTY = UUID.fromString("00000000-0000-0000-0000-000000000000");

	private final DataInputStream input;
	private final DataOutputStream output;

	private UUID uuid = UUID_EMPTY;
	private String name = "";

	public User(final Socket client) throws IOException {
		client.setTcpNoDelay(true);
		client.setSoTimeout(2000); // 2 seconds
		this.input = new DataInputStream(client.getInputStream());
		this.output = new DataOutputStream(client.getOutputStream());
		final AtomicInteger id = new AtomicInteger(0);
		CompletableFuture.runAsync(() -> {
			boolean disconnect = false;
			try {
				this.name = this.in().readUTF();
				this.out().writeUTF((this.uuid = UUIDCache.fastUUID()).toString());
				if (!this.in().readUTF().equalsIgnoreCase(this.uuid().toString()))
					disconnect = true;
				Thread.currentThread().setName(String.format("Worker-%s", this.uuid().toString().split("-")[0]));
			} catch (IOException ignored) {}
			do {
			} while (!disconnect);
			try {
				this.in().close();
				this.out().close();
				client.close();
				ChattyServer.get().getLogger().info("dropped {}", client.getInetAddress().getHostAddress());
			} catch (IOException ignored) {}
		});
		// new Thread(() -> {
		// }, String.format("User-%d", COUNT.incrementAndGet())).start();
	}

	public DataInputStream in() {
		return this.input;
	}

	public DataOutputStream out() {
		return this.output;
	}

	public String name() {
		return this.name;
	}

	public UUID uuid() {
		return this.uuid;
	}
}
