package zip.luzey.chatty.server;

import zip.luzey.chatty.api.ChannelHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public record KeepAliveExec(ChannelHandler user) {
	public void run() {
		Executors.newScheduledThreadPool(1, runnable -> {
			final Thread thread = new Thread(runnable, "KeepAlive-%d");
			thread.setDaemon(false);
			return thread;
		}).scheduleAtFixedRate(() -> {
			/* TODO: ...
			if (networkPlayer.isChannelUsable())
			this.networkPlayer.send(new ClientKeepAlivePacket(this.timer().getTimeIn(TimeUnit.MILLISECONDS)));*/
		}, 0L, 2L, TimeUnit.SECONDS);
	}
}
