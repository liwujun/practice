package com.wuma.file.trans;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

/**
 * Created by wuma
 * on 2016/7/19 at 15:10
 */
public class FileClient {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt("8080");
    static final String host = System.getProperty("host", "127.0.0.1");
    static final String FILE = System.getProperty("file", "D:\\Downloads\\Mycat_V1.6.0.pdf");
    static final String BASE_URL = System.getProperty("base_url", "http://127.0.0.1:8080/");

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            File file = new File(FILE);
            if (!file.canRead()) {
                throw new FileNotFoundException(FILE);
            }
            b.group(group).channel(NioSocketChannel.class).remoteAddress(
                    new InetSocketAddress(host, PORT)
            ).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new FileClientHandler());
                }
            });

            //set headers
            HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

            ChannelFuture f = b.connect().sync();
            Channel channel = f.channel();
            URI uriTrans = new URI(BASE_URL + "trans");
            HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
                    HttpMethod.POST, uriTrans.toASCIIString());
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaderNames.HOST, host);
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            headers.set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP + "," + HttpHeaderValues.DEFLATE);

            headers.set(HttpHeaderNames.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaderNames.ACCEPT_LANGUAGE, "fr");
            headers.set(HttpHeaderNames.REFERER, uriTrans.toString());
            headers.set(HttpHeaderNames.USER_AGENT, "Netty Simple Http Client side");
            headers.set(HttpHeaderNames.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            headers.set(
                    HttpHeaderNames.COOKIE, io.netty.handler.codec.http.cookie.ClientCookieEncoder.STRICT.encode(
                            new io.netty.handler.codec.http.cookie.DefaultCookie("my-cookie", "foo"),
                            new io.netty.handler.codec.http.cookie.DefaultCookie("another-cookie", "bar"))
            );

            HttpPostRequestEncoder bodyRequestEncoder =
                    new HttpPostRequestEncoder(factory, request, true);
            //加入传输文件
            bodyRequestEncoder.addBodyFileUpload("myfile", file, "application/x-zip-compressed", false);
            List<InterfaceHttpData> bodylist = bodyRequestEncoder.getBodyListAttributes();
            bodyRequestEncoder.setBodyHttpDatas(bodylist);
            bodyRequestEncoder.finalizeRequest();
            channel.write(request);
            System.out.println("write a request");
            channel.flush();
            System.out.println("flush a request");

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
