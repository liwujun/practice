package com.wuma.netty.serializable;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhang on 2016/2/24.
 */
 class SubReqClientHandler extends ChannelHandlerAdapter {
    public SubReqClientHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReq subReq(int i) {
        SubscribeReq req = new SubscribeReq();
        req.setAddress("北京市海淀区中关村二期网易");
        req.setPhoneNumber("xxxxxxxxxxx");
        req.setSubReqID(i);
        req.setUserName("liwujun");
        return req;
    }
}
