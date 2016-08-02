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
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.net.URI;

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

        String postSimple, postFile, get;
        if (BASE_URL.endsWith("/")) {
            postFile = BASE_URL + "formpostmultipart";
        } else {
            postFile = BASE_URL + "/formpostmultipart";
        }
        URI uriFile = new URI(postFile);
        File file = new File(FILE);
        if (!file.canRead()) {
            throw new FileNotFoundException(FILE);
        } else {
            System.out.println("file can be read.");
        }

        EventLoopGroup group = new NioEventLoopGroup();

        // setup the factory: here using a mixed memory/disk based on size threshold
        HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if MINSIZE exceed

        DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file on exit (in normal exit)
        DiskFileUpload.baseDirectory = null; // system temp directory
        DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on exit (in normal exit)
        DiskAttribute.baseDirectory = null; // system temp directory


        try {
            Bootstrap b = new Bootstrap();
            if (!file.canRead()) {
                throw new FileNotFoundException(FILE);
            } else {
                System.out.println("file can be read.");
            }
            b.group(group).channel(NioSocketChannel.class).remoteAddress(
                    new InetSocketAddress(host, PORT)
            ).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("codec", new HttpClientCodec());

                    // Remove the following line if you don't want automatic content decompression.
                    ch.pipeline().addLast("inflater", new HttpContentDecompressor());

                    // to be used since huge file transfer
                    ch.pipeline().addLast("chunkedWriter", new ChunkedWriteHandler());
                    ch.pipeline().addLast(new FileClientHandler());
                }
            });


            ChannelFuture f = b.connect();
            Channel channel = f.sync().channel();
            HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
                    HttpMethod.POST, uriFile.toASCIIString());

            HttpHeaders headers = request.headers();
            headers.set(HttpHeaderNames.HOST, host);
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            headers.set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP + "," + HttpHeaderValues.DEFLATE);

            headers.set(HttpHeaderNames.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaderNames.ACCEPT_LANGUAGE, "fr");
            headers.set(HttpHeaderNames.REFERER, uriFile.toString());
            headers.set(HttpHeaderNames.USER_AGENT, "Netty Simple Http Client side");
            headers.set(HttpHeaderNames.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            //connection will not close but needed
            // headers.set("Connection","keep-alive");
            // headers.set("Keep-Alive","300");

            headers.set(
                    HttpHeaderNames.COOKIE, io.netty.handler.codec.http.cookie.ClientCookieEncoder.STRICT.encode(
                            new io.netty.handler.codec.http.cookie.DefaultCookie("my-cookie", "foo"),
                            new io.netty.handler.codec.http.cookie.DefaultCookie("another-cookie", "bar"))
            );


            HttpPostRequestEncoder bodyRequestEncoderLast =
                    new HttpPostRequestEncoder(factory, request, true);

            bodyRequestEncoderLast.addBodyAttribute("getform", "POST");
            bodyRequestEncoderLast.addBodyAttribute("info", "first value");
            bodyRequestEncoderLast.addBodyAttribute("secondinfo", "secondvalue");
            bodyRequestEncoderLast.addBodyFileUpload("myfile", file, "application/x-zip-compressed", false);
            bodyRequestEncoderLast.finalizeRequest();

            channel.write(request);

            System.out.println("write a request");

            if (bodyRequestEncoderLast.isChunked()) {
                channel.write(bodyRequestEncoderLast);
                System.out.println("bodyRequestEncoder is chunked.");
            }
            channel.flush();

            bodyRequestEncoderLast.cleanFiles();

            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
            factory.cleanAllHttpData();
        }
    }

}
