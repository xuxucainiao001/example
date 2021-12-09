package cn.toseektech.example.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.lang.UUID;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 * 
 * @author xuxu
 *
 */
public class ProtocolHandler extends SimpleChannelInboundHandler<String> {

	private Logger logger = LoggerFactory.getLogger(ProtocolHandler.class);

	private volatile Channel channel;

	private volatile Device device;
	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.channel = ctx.channel();
		ctx.fireChannelActive();
		GlobalContext.totalLinks.incrementAndGet();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		if (device == null) {
			if ("1".equals(msg)) {
				this.device = new Device(UUID.fastUUID().toString());
				GlobalContext.channelMaps.put(this.device, this.channel);
				logger.info("连接认证完成：{}" , this.channel);
				ctx.fireChannelRead(msg);
			} else {
				logger.info("连接未认，强制关闭：{}" , this.channel);
				channel.close();
			}
		} else {
			if ("2".equals(msg)) {
				logger.info("连接关闭：{}" , this.channel);			
				GlobalContext.channelMaps.remove(device).close();
				GlobalContext.totalLinks.decrementAndGet();
			} else {
				if (GlobalContext.channelMaps.containsKey(device)) {
					logger.info("服务接受到消息：{}" , msg);
					ctx.fireChannelRead(msg);
				} else {
					logger.info("连接未认，强制关闭：{}" , this.channel);
					GlobalContext.channelMaps.remove(device).close();
					GlobalContext.totalLinks.decrementAndGet();
				}
			}
		}
	}


	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (device != null) {
			GlobalContext.channelMaps.remove(device);
			GlobalContext.totalLinks.decrementAndGet();
		}
		logger.info("{},Disconnect", ctx.channel());
		ctx.fireChannelInactive();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("Netty服务处理发生异常:", cause);
	}

}

