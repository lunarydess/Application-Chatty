package zip.luzey.chatty.api.packet;

import io.netty.buffer.ByteBuf;

public abstract class AbstractPacket {
	// @formatter:off
	public abstract void read(ByteBuf buf);
	public abstract void write(ByteBuf buf);
	public abstract int id();
	// @formatter:on

	public enum State {AUTH, USER}
}
