package com.wuma.file.trans;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by liwujun
 * on 2016/7/19 at 15:20
 */
public class FileClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("FileClient conn a server");
        ctx.write(Unpooled.copiedBuffer("HELO,I'm a client", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        System.out.println("Client received:" + ByteBufUtil.hexDump(byteBuf.readBytes(byteBuf.readableBytes())));
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
