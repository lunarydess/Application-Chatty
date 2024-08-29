package zip.luzey.chatty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollIoHandler;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueIoHandler;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zip.luzey.chatty.api.ChannelHandler;
import zip.luzey.chatty.api.ChattyInitializer;
import zip.luzey.tinyevents.TinyEvents;

import java.util.IdentityHashMap;

public class ChattyServer {
	public static boolean
		 EPOLL = Epoll.isAvailable(),
		 KQUEUE = KQueue.isAvailable();

	private final Logger logger = LoggerFactory.getLogger(ChattyServer.class);
	private final TinyEvents events = new TinyEvents(
		 IdentityHashMap::new,
		 throwable -> this.logger.error("event error", throwable)
	);

	private final String host;
	private final int port;

	private final EventLoopGroup bossGroup;
	private final EventLoopGroup workerGroup;
	private final Class<? extends ServerChannel> channel;
	private final ChattyInitializer networkInitializer;

	private ServerBootstrap serverBootstrap;
	private ChannelFuture channelFuture;

	public ChattyServer(TinyEvents eventManager) {
		this("127.0.0.1", 1337, eventManager);
	}

	public ChattyServer(
		 int port,
		 TinyEvents eventManager
	) {
		this("127.0.0.1", port, eventManager);
	}

	public ChattyServer(
		 String host,
		 int port,
		 TinyEvents eventManager
	) {
		this.host = host;
		this.port = port;

		this.networkInitializer = new ChattyInitializer(
			 (tinyEvents, channel1) -> new ChannelHandler(channel1, tinyEvents),
			 this.events
		);

		this.bossGroup =
			 this.workerGroup =
				  new MultiThreadIoEventLoopGroup(
						EPOLL ? (KQUEUE ?
						         KQueueIoHandler.newFactory() :
						         EpollIoHandler.newFactory()) :
						NioIoHandler.newFactory()
				  );

		this.channel =
			 EPOLL ? (KQUEUE ?
			          KQueueServerSocketChannel.class :
			          EpollServerSocketChannel.class) :
			 NioServerSocketChannel.class;
	}

	public void start() {
		this.channelFuture = (this.serverBootstrap = new ServerBootstrap()
			 .channel(channel)

			 .childOption(ChannelOption.IP_TOS, 0xA) // AF 11
			 .childOption(ChannelOption.TCP_NODELAY, true)
			 .childOption(ChannelOption.TCP_FASTOPEN_CONNECT, false)
			 .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)

			 .option(ChannelOption.SO_REUSEADDR, true)

			 .group(bossGroup, workerGroup)
			 .childHandler(networkInitializer)

			 .localAddress(host, port)).bind().syncUninterruptibly();
	}

	public Logger getLogger() {
		return this.logger;
	}

	public TinyEvents getEvents() {
		return this.events;
	}

	public String getHost() {
		return this.host;
	}

	public int getPort() {
		return this.port;
	}

	public EventLoopGroup getBossGroup() {
		return this.bossGroup;
	}

	public EventLoopGroup getWorkerGroup() {
		return workerGroup;
	}

	public Class<? extends ServerChannel> getChannel() {
		return this.channel;
	}

	public ServerBootstrap getServerBootstrap() {
		return this.serverBootstrap;
	}

	public ChannelFuture getFuture() {
		return this.channelFuture;
	}

	public ChattyInitializer getNetworkInitializer() {
		return this.networkInitializer;
	}
}
