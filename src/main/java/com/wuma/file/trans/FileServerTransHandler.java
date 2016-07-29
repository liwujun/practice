package com.wuma.file.trans;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by wuma
 * on 2016/7/19 at 15:36
 */
public class FileServerTransHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final HttpDataFactory factory =
            new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed
    private HttpPostRequestDecoder decoder;
    private final StringBuilder responseContent = new StringBuilder();
    private HttpRequest request;
    private boolean readingChunks;
    //这个值用作装大块内容的
    private HttpData partialContent;
    private File dest;


    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("a conn from client active.");
        dest=new File("d:\\x.pdf");
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("a conn from client is inactive.");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("Server begin to read");
        if (msg instanceof HttpRequest) {
            System.out.println("receive a HttpRequest ");
            HttpRequest request = this.request = (HttpRequest) msg;
            decoder = new HttpPostRequestDecoder(factory, request);

        } else {
            System.out.println("Receive a msg not a HttpRequest");
        }

        readingChunks = HttpUtil.isTransferEncodingChunked(request);

        if (decoder != null) {
            if (msg instanceof HttpContent) {
                System.out.println("Server received a httpContent");
                //server收到了大块内容
                HttpContent chunk = (HttpContent) msg;
                try {
                    decoder.offer(chunk);
                } catch (HttpPostRequestDecoder.ErrorDataDecoderException e1) {
                    e1.printStackTrace();
                    responseContent.append(e1.getMessage());
                    ctx.channel().close();
                    return;
                }
                // example of reading chunk by chunk (minimize memory usage due to
                // Factory)
                readHttpDataChunkByChunk();

                if (chunk instanceof LastHttpContent) {
//                    writeResponse(ctx.channel());
                    readingChunks = false;

                    reset();
                }
            } else {
                System.out.println("Server received not a httpcontent");
            }
        }
        System.out.println("readingChunks:" + readingChunks);
        System.out.println("Server received:" + msg);

    }

    /**
     * Example of reading request by chunk and getting values from chunk to chunk
     */
    private void readHttpDataChunkByChunk() throws IOException {
        System.out.println("readHttpDataChunkByChunk......");
        try {
            while (decoder.hasNext()) {
                System.out.println("decoder hasNext()...");
                InterfaceHttpData data = decoder.next();
                if (data != null) {
                    // check if current HttpData is a FileUpload and previously set as partial
                    if (partialContent == data) {
                        System.out.println(" 100% (FinalSize: " + partialContent.length() + ")");
                        partialContent = null;
                    }
                    try {
                        // new value，写response的。写文件到服务器磁盘上
                        writeHttpData(data);
                    } finally {
                        data.release();
                    }
                }
            }
            System.out.println("decoder has no next()...");
            // Check partial decoding for a FileUpload
            InterfaceHttpData data = decoder.currentPartialHttpData();
            if (data != null) {
                System.out.println("decoder is not null....");
                StringBuilder builder = new StringBuilder();
                if (partialContent == null) {
                    partialContent = (HttpData) data;
                    if (partialContent instanceof FileUpload) {

                        builder.append("Start  FileUpload: ")
                                .append(((FileUpload) partialContent).getFilename()).append(" ");
                    } else {
                        builder.append("Start  Attribute: ")
                                .append(partialContent.getName()).append(" ");
                    }
                    builder.append("(DefinedSize: ").append(partialContent.definedLength()).append(")");
                }
                if (partialContent.definedLength() > 0) {
                    builder.append(" ").append(partialContent.length() * 100 / partialContent.definedLength())
                            .append("% ");
                    System.out.println(builder.toString());
                } else {
                    builder.append(" ").append(partialContent.length()).append(" ");
                    System.out.println(builder.toString());
                }
            }else {
                System.out.println("decoder's current PartialHttpData is null");
            }
        } catch (HttpPostRequestDecoder.EndOfDataDecoderException e1) {
            // end
            System.out.println("EndOFDataDecoderException");
            responseContent.append("\r\n\r\nEND OF CONTENT CHUNK BY CHUNK\r\n\r\n");
        }
    }

    private void writeHttpData(InterfaceHttpData data) throws IOException {
        System.out.println("writeHttpData");
        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
            System.out.println("data.getHttpDataType()=InterfaceHttpData.HttpDataType.Attribute");
            Attribute attribute = (Attribute) data;
            String value;
            try {
                value = attribute.getValue();
            } catch (IOException e1) {
                // Error while reading data from File, only print name and error
                e1.printStackTrace();
                responseContent.append("\r\nBODY  Attribute: " + attribute.getHttpDataType().name() + ": "
                        + attribute.getName() + " Error while reading value: " + e1.getMessage() + "\r\n");
                return;
            }
            if (value.length() > 100) {
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType().name() + ": "
                        + attribute.getName() + " data too long\r\n");
            } else {
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType().name() + ": "
                        + attribute + "\r\n");
            }
        } else {
            System.out.println("data.getHttpDataType!=....");
            responseContent.append("\r\nBODY FileUpload: " + data.getHttpDataType().name() + ": " + data
                    + "\r\n");
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
                FileUpload fileUpload = (FileUpload) data;
                if (fileUpload.isCompleted()) {
                    if (fileUpload.length() < 10000) {
                        responseContent.append("\tContent  of file\r\n");
                        try {
                            responseContent.append(fileUpload.getString(fileUpload.getCharset()));
                        } catch (IOException e1) {
                            // do nothing for the example
                            e1.printStackTrace();
                        }
                        responseContent.append("\r\n");
                    } else {
                        responseContent.append("\tFile  too long to be printed out:" + fileUpload.length() + "\r\n");
                    }
                     fileUpload.isInMemory();// tells if the file is in Memory
                    // or on File
                    System.out.println("fileUpload.renameTo");
                     fileUpload.renameTo(dest); // enable to move into another
                    // File dest
                    // decoder.removeFileUploadFromClean(fileUpload); //remove
                    // the File of to delete file
                } else {
                    responseContent.append("\tFile to be continued but should not!\r\n");
                }
            }
        }
    }

    private void reset() {
        request = null;

        // destroy the decoder to release all resources
        decoder.destroy();
        decoder = null;
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
