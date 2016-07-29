package com.wuma.file.trans;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by wuma
 * on 2016/7/19 at 15:36
 */
public class FileServerTransHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final HttpDataFactory factory =
            new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed
    private HttpPostRequestDecoder decoder;
    private final StringBuilder responseContent = new StringBuilder();
    private HttpRequest request;
    private boolean readingChunks;

    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("a conn from client active.");
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("a conn from client is inactive.");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("Server begin to read");
        if (msg instanceof HttpRequest) {
            System.out.println("receive a HttpRequest ");
            HttpRequest request = this.request = (HttpRequest) msg;

        } else {
            System.out.println("Receive a msg not a HttpRequest");
        }

        decoder = new HttpPostRequestDecoder(factory, request);
        readingChunks = HttpUtil.isTransferEncodingChunked(request);

        System.out.println("readingChunks:" + readingChunks);
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
        System.out.println("Server read complete");
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
