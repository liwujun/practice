package com.wuma.netty.testaio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.List;

/**
 * Created by user on 17/5/26.
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncHandler> {


    @Override
    public void completed(AsynchronousSocketChannel channel, AsyncHandler asyncHandler) {
        asyncHandler.getChannel().accept(asyncHandler,this);
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        channel.read(buffer,buffer,new ReadHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AsyncHandler attachment) {
        exc.printStackTrace();

    }
}
