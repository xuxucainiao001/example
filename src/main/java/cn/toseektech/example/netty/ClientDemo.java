package cn.toseektech.example.netty;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientDemo {

	private static final Logger logger = LoggerFactory.getLogger(ClientDemo.class);

	private static Channel CHANNEL;

	private static Scanner SCANNER = new Scanner(System.in);

	public static void main(String[] args) throws InterruptedException {

		Bootstrap bootstrap = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				logger.info("正在连接中...");
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new StringEncoder())
				.addLast(new ClientMessageEncoder())						
				.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4))
				.addLast(new StringDecoder())
				.addLast(new ClientMessageHandler());
			}
		});
		// 发起同步连接请求，绑定连接端口和host信息
		final ChannelFuture future = bootstrap.connect("127.0.0.1", 2000).sync();
		logger.info("连接成功...");
		CHANNEL = future.awaitUninterruptibly().channel();

		while (true) {
			System.out.println("请输入发送的消息:");
			String line = SCANNER.nextLine();
			if (StringUtils.isEmpty(line)) {
				continue;
			}
			if ("exit".equals(line)) {
				group.shutdownGracefully(); // 关闭线程组
				SCANNER.close();
				return;
			}
			System.out.println(">>>" + line);
			CHANNEL.writeAndFlush(line).addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess()) {
						logger.info("发送消息失败:{0}", future.cause());
					}
				}
			});
		}

	}

}
