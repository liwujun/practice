package com.wuma.netty.sample3;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by liwujun
 * on 2016/2/14 at 11:34
 */
public class HelloClient {
    /**
     * Netty �ͻ��˴��� * *
     *
     * @author liwujun
     */
    public static void main(String args[]) {
        // Client����������
        ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        // ����һ������������Ϣ�͸�����Ϣ�¼�����(Handler)
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new HelloClientHandler());
            }
        });
        // ���ӵ����ص�8000�˿ڵķ����
        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));
    }

    private static class HelloClientHandler extends SimpleChannelHandler {
        /**
         * ���󶨵�����˵�ʱ�򴥷�����ӡ"Hello world, I'm client."
         */
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
            System.out.println("Hello world, I'm client.");
        }

    }
}
