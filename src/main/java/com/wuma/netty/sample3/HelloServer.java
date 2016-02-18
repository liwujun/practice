package com.wuma.netty.sample3;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by liwujun
 * on 2016/2/14 at 11:29
 */
public class HelloServer {
    public static void main(String args[]) {
        // Server����������
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        // ����һ������ͻ�����Ϣ�͸�����Ϣ�¼�����(Handler)
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new HelloServerHandler());
            }
        });
        // ����8000�˿ڹ��ͻ��˷��ʡ�
        bootstrap.bind(new InetSocketAddress(8000));
    }

    private static class HelloServerHandler extends SimpleChannelHandler {
        /***
         * ���пͻ��˰󶨵�����˵�ʱ�򴥷�����ӡ"Hello world, I'm server."
         */
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
            System.out.println("Hello world, I'm server.");
        }
    }


}
