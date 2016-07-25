package com.wuma.netty.serverconfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by wuma
 * on 2016/2/18 at 16:01
 * 异步绑定端口
 */
public class BootstrapingServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
//        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
//                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
//                    @Override
//                    protected void channelRead0(ChannelHandlerContext ctx,
//                                                ByteBuf msg) throws Exception {
//                        System.out.println("Received data");
//                        msg.clear();
//                    }
//                });
        ChannelFuture f = b.bind(2048);
        f.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("bound fail");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
