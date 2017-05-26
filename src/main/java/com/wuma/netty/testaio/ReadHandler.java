package com.wuma.netty.testaio;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.List;

/**
 * Created by user on 17/5/26.
 */
public class ReadHandler implements CompletionHandler<Integer,ByteBuffer> {

    Logger LOGGER=Logger.getLogger(ReadHandler.class);
    private AsynchronousSocketChannel channel;
    public ReadHandler(AsynchronousSocketChannel channel){
        this.channel=channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] message=new byte[attachment.remaining()];
        attachment.get(message);
        try {
            String response=new String(message,"utf-8");
            LOGGER.info("server Received:"+response);
            doEcho();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void doEcho(){
        byte[] bytes="received!\n".getBytes();
        ByteBuffer buffer=ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()){
                    channel.write(attachment,attachment,this);
                }else {
                    ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                    channel.read(readBuffer,readBuffer,new ReadHandler(channel));
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
