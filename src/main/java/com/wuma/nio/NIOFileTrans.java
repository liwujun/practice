//package com.wuma.nio;
//
//import org.junit.Test;
//
//import java.io.*;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.nio.ByteBuffer;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.ServerSocketChannel;
//import java.nio.channels.SocketChannel;
//import java.util.Iterator;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
///**
// * Created by liwujun
// * on 2016/2/6 at 11:25
// */
//
//public class NIOFileTrans {
//    public static final int PORT = 21213;//端口号
//    //发送缓冲和接收缓冲区大小
//    public static final int BUFFER_SIZE = 1024 * 1024;
//    //线程池大小
//    public static final int MAX_POOL_SIZE = 6;
//
//    public class FileTransContext implements Runnable {
//        public ByteBuffer buffer;//发送缓冲
//        public InputStream inputStream;//发送流（读取文件流）
//        public boolean isFinished = false;//发送成功标识
//        public SocketChannel channel;//套接字通道（输出流）
//        //当前发送线程任务（用于查询当前线程是否执行结束）
//        public Future<?> future;
//
//        //一次发送
//        public boolean sendFile() throws IOException {
//            //如果当前的buffer已经传送结束，则从inputStream中读取新的buffer
//            if (!buffer.hasRemaining()) {
//                if (isFinished)//如果文件已经发送完毕，直接返回
//                {
//                    return true;//发送结束
//                }
//                getNextBuffer();//从文件中获取下一段数据
//            }
//            channel.write(buffer);//向socket写出数据
//            return false;//文件仍未发送完毕
//        }
//
//        private void getNextBuffer() throws IOException {
//            //获取一段新数据
//            int i = inputStream.read(buffer.array());
//            if (i == -1)//如果读到文件末尾
//            {
//                isFinished = true;//文件输出结束
//            } else {
//                buffer.position(0);//重新组织缓冲区
//                buffer.limit(i);
//            }
//        }
//
//
//        public void run() {
//            try {
//                sendFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public class ReadFileName implements Runnable {
//        public SocketChannel channel;//数据将要传输的socket通道
//        public String fileName = "";//接收到的文件名
//        public ByteBuffer buffer;//输入缓冲区
//        public boolean isReadFinished = false;//读取操作完成标识
//        public Future<?> future;//用于判断当前线程状态
//        public SelectionKey key;//对应的选择键
//
//        public synchronized void run() {
//            buffer.clear();//清空缓冲区
//            try {
//                channel.read(buffer);//从通道中读取数据
//                buffer.flip();//将数据翻转，用于读出
//                if (buffer.array()[buffer.limit() - 1] == -1)//接收到-1表示读到了末尾
//                {
//                    fileName += new String(buffer.array(), 0, buffer.limit() - 1);//组织字符串
//                    isReadFinished = true;//读取结束
//                } else {
//                    fileName += new String(buffer.array(), 0, buffer.limit());//组织字符串
//                }
//
//            } catch (IOException e) {
//            }
//        }
//    }
//
//    @Test
//    public void service() throws IOException {
//        ServerSocketChannel server = ServerSocketChannel.open();//创建Server
//        server.socket().bind(new InetSocketAddress(PORT));//绑定端口
//        server.configureBlocking(false);//设置非阻塞模式
//        ExecutorService pool = Executors.newFixedThreadPool(MAX_POOL_SIZE);   //创建线程池
//
//        Selector select = Selector.open(); //创建监听器
//        server.register(select, SelectionKey.OP_ACCEPT);//将server的accept事件放入监听器
//
//        while (true) {
//            select.select();//阻塞等待，新的事件到来
//            Iterator<SelectionKey> keys = select.selectedKeys().iterator();
//            //Set<SelectionKey> keys = select.selectedKeys();//获取发生的事件
//            //for(SelectionKey key:keys)//遍历处理事件
//            while (keys.hasNext()) {
//                SelectionKey key = keys.next();
//                if (key.isAcceptable())//如果是远程有请求连接
//                {
//                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();//获取服务
//                    SocketChannel sc = ssc.accept();//创建远程的连接
//                    sc.configureBlocking(false);//设置socket连接为非阻塞模式
//                    sc.register(select, SelectionKey.OP_READ);//将socket连接的读事件注册
//                }
//                if (key.isReadable())//发生了读事件
//                {
//                    SocketChannel sc = (SocketChannel) key.channel();//获取socket
//
//                    ReadFileName read = (ReadFileName) key.attachment();//获取socket的环境参量
//
//                    if (read == null)//如果环境参量为空，表示首次进行读取
//                    {
//                        read = new ReadFileName();//创建环境参量
//                        read.buffer = ByteBuffer.allocate(BUFFER_SIZE);//创建缓冲区
//                        read.channel = sc;//将通道赋值到环境中
//                        read.future = pool.submit(read);//将读取任务提交到线程池
//                        read.key = key;//将当前key放入环境中
//                        //注册写入事件，注意这时并没有真的可以写入信息，但是仍然需要提前注册这个事件
//                        sc.register(select, SelectionKey.OP_WRITE);
//                        key.attach(read);//向key添加环境属性
//                    } else if (read.future.isDone() && read.isReadFinished == false) {//表明，上次执行的读线程已经读取结束，但是并未读取传入信息的末尾
//                        pool.submit(read);//再次执行新的线程，继续读取剩余数据
//                    }
//                }
//                if (key.isWritable())//当通道可写
//                {
//                    SocketChannel sc = (SocketChannel) key.channel();//获取socket
//                    if (key.attachment() instanceof ReadFileName)//如果环境是从客户端读文件名
//                    {
//                        ReadFileName read = (ReadFileName) key.attachment();//获取环境
//                        if (read.isReadFinished)//如果读文件名操作已经结束，那么要生成写环境
//                        {
//                            BufferedInputStream inputStream = null; //创建文件输入流
//
//                            try {
//                                //初始化输入流
//                                inputStream = new BufferedInputStream(new FileInputStream(new File(read.fileName)));
//                            } catch (IOException e) {//出现异常要关闭socket
//                                System.err.println("需要下载的文件不存在");
//                                sc.close();
//                            }
//                            FileTransContext transContext = new FileTransContext();//创建新的文件传输环境
//                            transContext.buffer = ByteBuffer.allocate(BUFFER_SIZE);
//                            transContext.buffer.limit(0);
//                            transContext.channel = sc;
//                            transContext.inputStream = inputStream;
//                            key.attach(transContext);//将新环境与key相关
//                        }
//                    } else if (key.attachment() instanceof FileTransContext)//当前环境是文件传送
//                    {
//                        FileTransContext context = (FileTransContext) key.attachment();//获取环境
//                        if (context.future == null)//第一次进行文件写操作
//                        {
//                            context.future = pool.submit(context);
//
//                        } else if (context.future.isDone() && !context.isFinished)//如果上次写操作结束，并且文件并没有传输完成
//                        {
//                            context.future = pool.submit(context);//进行下一个文件块的写入
//                        } else if (context.future.isDone())//如果上次写入已经结束，并且文件传输结束
//                        {
//                            sc.close();//关闭套接字
//                        }
//                    }
//
//                }
//                //keys.remove(key);//移除本次处理的键
//                keys.remove();
//            }
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        new NIOFileTrans().service();
//    }
//
//
//    @Test
//    public void client() throws UnknownHostException, IOException {
//        //文件要输出的位置
//        OutputStream out = new FileOutputStream(new File("I:\\电影\\The.Lincoln.Lawyer.2011.R5.DVDRiP.XViD[林肯律师]test.rmvb"));
//        Socket socket = new Socket("localhost", PORT);
//
//        //获取文件输入流（网络服务器）
//        InputStream in = socket.getInputStream();
//        //获取网络服务器的输入流（客户端的输出流，用于输出想要获取的文件名）
//        OutputStream socketOut = socket.getOutputStream();
//
//        //写出需要读取的文件名
//        socketOut.write("I:\\电影\\The.Lincoln.Lawyer.2011.R5.DVDRiP.XViD[林肯律师].rmvb".getBytes());
//        socketOut.write(new byte[]{-1});//输出终止符
//
//        byte[] buffer = new byte[BUFFER_SIZE];//新创建一个读入缓冲区
//        int i = 0;
//        while (true) {
//            i = in.read(buffer);//从网络读入数据
//            if (i == -1)//读取到文件末尾
//                break;
//            out.write(buffer, 0, i);//向文件中写出
//        }
//        socket.close();//关闭套接字
//        out.close();//关闭文件
//    }
//}
//
//
