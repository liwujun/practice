package com.wuma.netty.sample5;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by liwujun
 * on 2016/2/24 at 11:15
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("the time server receive order : " + body);
        String currentTime = "QUERY TIME ORDER".equals(body) ? new
                java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        ByteBuf resq = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resq);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}
