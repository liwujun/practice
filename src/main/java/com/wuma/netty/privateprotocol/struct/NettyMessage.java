package com.wuma.netty.privateprotocol.struct;

/**
 * Created by liwujun
 * on 2016/2/25 at 10:56
 */
public final class NettyMessage {
    private Header header;
    private Object body;

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    public final Header getHeader() {
        return header;
    }

    public final void setHeader(Header header) {
        this.header = header;
    }

    public String toString() {
        return "NettyMessage [header=" + header + ",body=" + body + "]";
    }
}
