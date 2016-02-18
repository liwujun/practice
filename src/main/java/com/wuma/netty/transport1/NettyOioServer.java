package com.wuma.netty.transport1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * Created by liwujun
 * on 2016/2/18 at 11:16
 */
public class NettyOioServer {
    public void server(int port) throws Exception {
        final ByteBuf buf = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("Hi!\r\n", CharsetUtil.UTF_8));
        //ʱ��ѭ����
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //?������������������
            ServerBootstrap b = new ServerBootstrap();
            //ʹ�� OIO����ģʽ ????
            b.group(group).channel(OioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    //ָ��ChannelInitializer ��ʼ��handlers
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {

                            //���һ����վ��handler �� ChannelPipeline
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    //���Ӻ�д��Ϣ���ͻ��ˣ�д����ر�����
                                    ctx.writeAndFlush(buf.duplicate())
                                            .addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            //�󶨷�������������
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            //�ͷ���������
            group.shutdownGracefully();
        }
    }

}

