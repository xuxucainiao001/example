package cn.toseektech.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ServerMessageEncoder extends MessageToByteEncoder<Command> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Command cmd, ByteBuf out) throws Exception {
		
		ByteBufAllocator alloc = ctx.alloc();
		CompositeByteBuf compositeBuffer = alloc.compositeBuffer();
		byte[] content = cmd.commandContent();
		int length = content.length;
		compositeBuffer.addComponents(
				true, 
				alloc.buffer().writeBytes(ByteUtils.intToByteArray(length)),
				alloc.buffer().writeBytes(content));
		ctx.writeAndFlush(compositeBuffer);
	}

}
