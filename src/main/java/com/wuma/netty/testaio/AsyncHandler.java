package com.wuma.netty.testaio;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * Created by user on 17/5/26.
 */
public class AsyncHandler implements Runnable{
    Logger LOGGER=Logger.getLogger(AsyncHandler.class);
    public CountDownLatch latch;
    private AsynchronousServerSocketChannel channel;

    public AsynchronousServerSocketChannel getChannel(){
        return channel;
    }
    public AsyncHandler(int port){
        try {
            channel =AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(port));
            LOGGER.info("Server Run!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void run() {
        latch=new CountDownLatch(1);

        channel.accept(this,new AcceptHandler());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
