package com.wuma.netty.testidle;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by user on 17/5/26.
 */
public class ServerHandler implements Runnable {
    private Logger LOGGER=Logger.getLogger(ServerHandler.class);
    private Selector selector;
    private ServerSocketChannel socketChannel;
    private volatile boolean started=false;
    public ServerHandler(int port){
        try {
            selector=Selector.open();
            socketChannel= ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.socket().bind(new InetSocketAddress(port),1024);
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            started=true;
            LOGGER.info("server has run!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        while (started){
            try {
                selector.select(1000);
                selector.select();
                Set<SelectionKey> keys=selector.selectedKeys();
                Iterator<SelectionKey> it=keys.iterator();
                LOGGER.info("keys' size:"+keys.size());
                SelectionKey key=null;
                while (it.hasNext()){
                    key=it.next();
                    it.remove();
                    handlekey(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(selector!=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void handlekey(SelectionKey key){
        if (key.isValid()){
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                try {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (key.isReadable()){
                SocketChannel sc= (SocketChannel) key.channel();
                ByteBuffer buffer=ByteBuffer.allocate(1024);
                try {
                    int readBytes=sc.read(buffer);
                    if (readBytes>0){
                        buffer.flip();
                        byte[] bytes=new byte[buffer.remaining()];
                        buffer.get(bytes);
                        String str=new String(bytes,"UTF-8");
                        LOGGER.info("server received:"+str);
                        echoResponse(sc);
                    }else if (readBytes<=0){
                        key.cancel();
                        sc.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void echoResponse(SocketChannel sc){
        byte[] bytes="Received!".getBytes();
        ByteBuffer byteBuffer=ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        try {
            sc.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
