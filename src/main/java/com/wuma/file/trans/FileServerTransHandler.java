package com.wuma.file.trans;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by liwujun
 * on 2016/7/19 at 15:36
 */
public class FileServerTransHandler extends ChannelInboundHandlerAdapter {


    public void channelActive(ChannelHandlerContext ctx) {
        ctx.write(Unpooled.copiedBuffer("I'm Server", CharsetUtil.UTF_8));
        ctx.flush();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Server received:" + msg);
        ByteBuf in = (ByteBuf) msg;
        ctx.write(msg);
        try {
            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
//            ReferenceCountUtil.release(msg); // (2)
        }
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
