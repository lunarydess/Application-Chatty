package zip.luzey.chatty.api.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.DecoderException;
import zip.luzey.chatty.api.ChannelHandler;
import zip.luzey.chatty.api.packet.AbstractPacket;
import zip.luzey.chatty.api.packet.PacketFactory;

import java.util.List;

public final class PacketCodec extends ByteToMessageCodec<AbstractPacket> {
	private final ChannelHandler handler;
	private final PacketFactory packets;

	public PacketCodec(
		 ChannelHandler handler,
		 PacketFactory packets
	) {
		this.handler = handler;
		this.packets = packets;
	}

	@Override
	protected void encode(
		 ChannelHandlerContext ctx,
		 AbstractPacket msg,
		 ByteBuf out
	) {
		int writeIndexStart = out.writerIndex();

		int packetId = -1;
		try {
			packetId = this.packets.create(msg.getClass()).id();
		} catch (Throwable ignored) {}

		if (packetId != -1) {
			// AbstractPacket.IO.writeVarInt(out, packetId); // TODO: ...
			msg.write(out);
			return;
		}

		out.writerIndex(writeIndexStart);
	}

	@Override
	protected void decode(
		 ChannelHandlerContext ctx,
		 ByteBuf in,
		 List<Object> out
	) {
		int readIndex = in.readerIndex();
		// int id = AbstractPacket.IO.readVarInt(in); TODO: ...
		int id = -1;

		if (id == -1) {
			in.readerIndex(readIndex);
			return;
		}

		AbstractPacket packet;
		try {
			packet = this.packets.create(this.packets.byStateId(this.handler.getState(), id));
		} catch (Throwable e) {
			throw new DecoderException("packet can't be constructed for id: %d".formatted(id));
		}

		packet.read(in);

		if (in.readableBytes() > 0) {
			throw new DecoderException("buffer still contains data for packet: %d - %s bytes: %d"
				                            .formatted(id, packet.getClass().getSimpleName(), in.readableBytes()));
		}

		if (in.readableBytes() != 0)
			throw new DecoderException("packet wasn't fully read! left over: %d".formatted(in.readableBytes()));

		out.add(packet);
	}

	public PacketFactory getPackets() {
		return this.packets;
	}

	public ChannelHandler getHandler() {
		return this.handler;
	}
}
