package com.wuma.file.trans;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by liwujun
 * on 2016/7/19 at 15:20
 */
public class FileClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("FileClient conn a server");
        ctx.write(Unpooled.copiedBuffer("HELO,I'm a client", CharsetUtil.UTF_8));
        System.out.println("FileClient write complete");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("FileClient channelRead0" + msg);
        System.out.println("Client received:" + ByteBufUtil.hexDump(
                msg.readBytes(msg.readableBytes())));
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
    public static void main(String[] args){
        Charset utf8=Charset.forName("UTF-8");
        ByteBuf buf=Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
        ByteBuf sliced=buf.slice(0,14);
        ByteBuf copy=buf.copy(0,14);
        System.out.println(buf.toString(utf8));
        System.out.println(sliced.toString(utf8));
        System.out.println(copy.toString(utf8));
    }
}
