package com.wuma.netty.sample2;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by liwujun
 * on 2016/2/14 at 10:15
 */
public class TestCase5 {
    public void testServer() {
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new TimeEncoder(), new TimeServerHandler3());
            }
        });
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        bootstrap.bind(new InetSocketAddress("localhost", 9999));
    }

    public void testClient() {
        //�����ͻ���channel�ĸ�����,����connection����
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new TimeDecoder());
                pipeline.addLast("encoder", new TimeEncoder());
                pipeline.addLast("handler", new TimeClientHandler3());
                return pipeline;
            }
        });
        //���������Ӵ���channel�ĸ�����(UDP),����client��server
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(
                "localhost", 9999));
        future.getChannel().getCloseFuture().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
    }

    @Test
    public void testNetty() {
        testServer();
        testClient();
    }
}
