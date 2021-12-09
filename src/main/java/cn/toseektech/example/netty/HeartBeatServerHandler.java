package cn.toseektech.example.netty;

import java.net.SocketAddress;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		Channel channel = ctx.channel();
		SocketAddress remoteAddress = channel.remoteAddress();
		if (evt instanceof IdleStateEvent) { // 2
			IdleStateEvent event = (IdleStateEvent) evt;
			String type = null;
			if (event.state() == IdleState.READER_IDLE) {
				// 关闭连接
				type = "read idle";
				channel.writeAndFlush(new HeatBeatCommand()).addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						future.channel().close();					
					}
				});
				channel.close();
			} else if (event.state() == IdleState.WRITER_IDLE) {
				type = "write idle";
			} else if (event.state() == IdleState.ALL_IDLE) {
				type = "all idle";
			}
			
			System.out.println(remoteAddress + "超时类型：" + type);	
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}
	
}

class HeatBeatCommand implements Command{
	
	private String message = "HeatBeat test";

	@Override
	public byte[] commandContent() {
		return message.getBytes();
	}
	
}
