package com.wuma.file.trans;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by liwujun
 * on 2016/7/19 at 15:36
 */
public class FileServerTransHandler extends ChannelInboundHandlerAdapter {
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(Unpooled.copiedBuffer("HELO,I'm Server", CharsetUtil.UTF_8));
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Server received:" + msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
