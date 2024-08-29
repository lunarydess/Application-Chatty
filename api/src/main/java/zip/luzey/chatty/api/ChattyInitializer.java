package zip.luzey.chatty.api;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.timeout.ReadTimeoutHandler;
import zip.luzey.tinyevents.TinyEvents;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public class ChattyInitializer extends ChannelInitializer<Channel> {
	private final BiFunction<TinyEvents, Channel, ChannelHandler> handler;
	private final TinyEvents events;

	public ChattyInitializer(
		 BiFunction<TinyEvents, Channel, ChannelHandler> handler,
		 TinyEvents events
	) {
		this.handler = handler;
		this.events = events;
	}

	@Override
	protected void initChannel(Channel channel) {
		channel.pipeline()
		       .addLast("timeout_handler", new ReadTimeoutHandler(10L, TimeUnit.SECONDS))
		       .addLast("frame_decoder", new LengthFieldBasedFrameDecoder(8192, 0, 4, 0, 4))
		       .addLast("frame_encoder", new LengthFieldPrepender(4))
		       .addLast("zlib_decoder", ZlibCodecFactory.newZlibDecoder(ZlibWrapper.ZLIB))
		       .addLast("zlib_encoder", ZlibCodecFactory.newZlibEncoder(ZlibWrapper.ZLIB, 128))
		       .addLast("user_handler", handler.apply(this.events, channel));
	}
}
