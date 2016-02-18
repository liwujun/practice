package com.wuma.netty.sample1;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by liwujun
 * on 2016/2/14 at 9:55
 */
public class TestCase {
    public void testServer() {
        //��ʼ��channel�ĸ����࣬Ϊ���������ṩ�������ݽṹ
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("handler", new HelloWorldServerHandler());
                return pipeline;
            }
        });
        //������������channel�ĸ�����,����connection����
        bootstrap.bind(new InetSocketAddress(8080));
    }


    public void testClient() {
        //�����ͻ���channel�ĸ�����,����connection����
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        //It means one same HelloWorldClientHandler instance
        // is going to handle multiple Channels and consequently the data will be corrupted.
        //����������������������õ�ChannelPipelineFactoryÿ�δ���һ��pipeline
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("handler", new HelloWorldClientHandler());
                return pipeline;
            }
        });
        //���������Ӵ���channel�ĸ�����(UDP),����client��server
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(
                "localhost", 8080));
        future.getChannel().getCloseFuture().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
    }


    @Test
    public void testNetty() {
        testServer();
        testClient();
    }
}
