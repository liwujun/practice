package com.wuma.netty.sample2;

import org.jboss.netty.channel.*;

/**
 * Created by liwujun
 * on 2016/2/14 at 10:10
 */
public class TimeServerHandler3 extends SimpleChannelHandler {
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
        Persons person = new Persons("ÖÜ½ÜÂ×123",31,10000.44);
        ChannelFuture future = e.getChannel().write(person);
        future.addListener(ChannelFutureListener.CLOSE);
    }
}
