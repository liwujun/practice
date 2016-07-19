package com.wuma.file.trans;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by liwujun
 * on 2016/7/19 at 15:36
 */
public class FileServerTransHandler extends ChannelInboundHandlerAdapter {
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("HELO,I'm Server");
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
