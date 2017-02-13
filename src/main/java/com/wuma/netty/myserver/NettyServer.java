package com.wuma.netty.myserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.log4j.Logger;

/**
 * Created by wuma
 * on 2017/2/13 at 16:52
 */
public class NettyServer {
    private static final Logger logger = Logger.getLogger(NettyServer.class);
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    NettyServer() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        ChannelFuture ch = b.bind(2048);
    }

    public static void main(String[] args) {
        NettyServer ns = new NettyServer();

    }
}
