package zip.luzey.chatty.server.util;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadLocalRandom;

public final class UUIDCache {
	private static final CopyOnWriteArraySet<UUID> UUIDS = new CopyOnWriteArraySet<>();

	public static UUID fastUUID() {
		final UUID uuid = new UUID(
			 ThreadLocalRandom.current().nextLong(),
			 ThreadLocalRandom.current().nextLong()
		);
		if (UUIDS.contains(uuid)) return fastUUID();
		UUIDS.add(uuid);
		return uuid;
	}

	public static void remove(final UUID uuid) {
		UUIDS.remove(uuid);
	}
}
