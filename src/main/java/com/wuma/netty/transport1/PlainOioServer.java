package com.wuma.netty.transport1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by liwujun
 * on 2016/2/18 at 11:00
 * ��ͨjava io �����
 */
public class PlainOioServer {
    public void server(int port) throws Exception {
        //bind server to port
        final ServerSocket socket = new ServerSocket(port);
        try {
            while (true) {
                //accept connection
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                //create new thread to handle connection
                new Thread(new Runnable() {

                    public void run() {
                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            //write message to connected client
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF8")));
                            out.flush();
                            //close connection once message written and flushed
                            clientSocket.close();
                        } catch (IOException e) {
                            try {
                                clientSocket.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }).start();//start thread to begin handling
            }
        } catch (Exception e) {
            e.printStackTrace();
            socket.close();
        }
    }

}

