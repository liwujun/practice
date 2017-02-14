package com.wuma.netty.myserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
        /**
         * An exception occured while executing the Java class.
         * null: InvocationTargetException: channel or channelFactory not set
         */
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);

        b.childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {

            }
        });
        ChannelFuture ch = b.bind(2048);
    }

    public static void main(String[] args) {
        NettyServer ns = new NettyServer();

    }
}
