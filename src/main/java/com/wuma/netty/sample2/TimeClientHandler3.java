package com.wuma.netty.sample2;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * Created by liwujun
 * on 2016/2/14 at 10:11
 */
public class TimeClientHandler3 extends SimpleChannelHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        Persons person = (Persons)e.getMessage();
        System.out.println(person);
        e.getChannel().close();
    }
}
