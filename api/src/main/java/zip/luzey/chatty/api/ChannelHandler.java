package zip.luzey.chatty.api;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jetbrains.annotations.NotNull;
import zip.luzey.chatty.api.codec.CryptoCodec;
import zip.luzey.chatty.api.event.PacketEvent.Receive;
import zip.luzey.chatty.api.event.PacketEvent.Send;
import zip.luzey.chatty.api.packet.AbstractPacket;
import zip.luzey.chatty.api.packet.AbstractPacket.State;
import zip.luzey.tinyevents.TinyEvents;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class ChannelHandler extends SimpleChannelInboundHandler<AbstractPacket> {
	private final Channel channel;
	private final TinyEvents events;
	private State state = State.AUTH;

	private String accountName;
	private UUID playerUUID;

	private boolean connected;

	public ChannelHandler(
		 Channel channel,
		 TinyEvents events
	) {
		this.channel = channel;
		this.events = events;
		this.connected = true;
	}

	public <P extends AbstractPacket> void send(P out) {
		if (channel == null || !channel.isActive())
			return;

		Send<P> send = new Send<>(out);
		this.getEvents().call(send);
		if (send.cancelled())
			return;
		out = send.getPacket();

		this.channel.writeAndFlush(out).addListener((ChannelFutureListener) channelFuture -> {
			if (channelFuture.isSuccess() || channelFuture.cause() == null)
				return;
			try {
				throw channelFuture.cause();
			} catch (Throwable ignored) {}
		});
	}

	public <P extends AbstractPacket> void receive(P in) {
		Receive<P> packet = new Receive<>(in);
		this.getEvents().call(packet);
		if (packet.cancelled()) {}
		// TODO: ... | logic
		// protocolHandler.packetReceived(abstractPacket);
	}

	@Override
	public void exceptionCaught(
		 ChannelHandlerContext ctx,
		 Throwable cause
	) throws Exception {
		if (cause instanceof IOException)
			return;
		super.exceptionCaught(ctx, cause);
	}

	public void setEncryption(KeyPair pair) {
		if (!this.isChannelUsable()) return;
		CryptoCodec codec;
		try {
			codec = new CryptoCodec(pair);
		} catch (NoSuchAlgorithmException exception) {
			throw new RuntimeException("algorithm ed25519 missing", exception);
		}
		channel.pipeline().addBefore(
			 "user_handler",
			 "encryption_codec",
			 codec
		);
	}

	public void disconnect(final String reason) {
		if (!this.isChannelUsable())
			return;

		// this.send(null); // TODO: ... | disconnect packet here lmao

		this.channel.flush().close().addListener(unused -> this.connected = false);
	}

	public boolean isChannelUsable() {
		return this.channel != null && this.channel.isActive();
	}

	@Override
	protected void channelRead0(
		 ChannelHandlerContext ctx,
		 AbstractPacket packet
	) {
		if (this.isChannelUsable())
			this.receive(packet);
	}

	@Override
	public void channelInactive(@NotNull ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);

		/* TODO: ...
		if (this.protocolHandler.getKeepAliveThread() != null &&
		    !this.protocolHandler.getKeepAliveThread().isInterrupted())
			this.protocolHandler.getKeepAliveThread().interrupt();

		eventManager.call(new PlayerDisconnectEvent(this));
		 */

		this.connected = false;
	}

	public Channel getChannel() {
		return channel;
	}

	public boolean isConnected() {
		return this.connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public UUID getPlayerUUID() {
		return this.playerUUID;
	}

	public void setPlayerUUID(UUID playerUUID) {
		this.playerUUID = playerUUID;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public TinyEvents getEvents() {
		return this.events;
	}
}
