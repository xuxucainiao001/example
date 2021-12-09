package cn.toseektech.example.netty;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;

public class ServerDemo {

	private static Logger logger = LoggerFactory.getLogger(ServerDemo.class);

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {

		// 处理accept事件的线程池
		EventLoopGroup bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
		// 处理非业务handler的线程池
		EventLoopGroup workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("worker"));
		// 处理业务handler用的线程池
		UnorderedThreadPoolEventExecutor bussinessExcutor = new UnorderedThreadPoolEventExecutor(25,
				new DefaultThreadFactory("bussiness"));
		// nettyServer启动
		ServerBootstrap serverBootStrap = new ServerBootstrap();
		serverBootStrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100).childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.TCP_NODELAY, true).childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()
						        .addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4))
								.addLast(new StringDecoder())
								.addLast(bussinessExcutor, new ProtocolHandler())
								.addLast(new IdleStateHandler(15, -1, 15))
								.addLast(new HeartBeatServerHandler())
								.addLast(new ServerMessageEncoder());
					}
				}).bind(2000).sync();
		logger.info("NettyServer配置启动完成");
		//监控启动
		GobalMonitor.start();
	}

}
