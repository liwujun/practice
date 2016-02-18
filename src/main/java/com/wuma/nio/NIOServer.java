package com.wuma.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by liwujun
 * on 2016/1/14 at 10:28
 */
public class NIOServer {
    public static final int PORT = 12112;
    public static final int TIMEOUT = 1000;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel listenChannel = ServerSocketChannel.open();
        listenChannel.configureBlocking(false);
        //bind to tcp port,serverSocketChannel don't support bind,
        //need inner's socket class
        listenChannel.socket().bind(new InetSocketAddress(PORT));
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (selector.select(TIMEOUT) == 0) {
                System.out.println(".");
                continue;
            }

            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()){
                    SocketChannel channel=listenChannel.accept();
                    channel.configureBlocking(false);
                    SelectionKey connkey=channel.register(selector,SelectionKey.OP_READ);

                }
            }

        }

    }
}
