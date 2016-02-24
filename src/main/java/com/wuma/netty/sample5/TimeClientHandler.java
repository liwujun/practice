package com.wuma.netty.sample5;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * Created by liwujun
 * on 2016/2/24 at 11:39
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    private static final Logger logger = Logger
            .getLogger(TimeClientHandler.class.getName());
//    private final ByteBuf firstMessage;

    private int counter;
    private byte[] req;

    public TimeClientHandler() {
//        byte[] req = "QUERY TIME ORDER".getBytes();
//        firstMessage = Unpooled.buffer(req.length);
//        firstMessage.writeBytes(req);
        String shenme = "QUERY TIME ORDER" + System.getProperty("line.separator");
        System.out.println(shenme);
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        ctx.writeAndFlush(firstMessage);
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, "UTF-8");
        String body = (String) msg;
        System.out.println("Now is: " + body + " ; the counter is: " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warning("Unexpected exception from downstream:" +
                cause.getMessage());
        ctx.close();
    }
}
