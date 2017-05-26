package com.wuma.netty.testidle;

/**
 * Created by user on 17/5/26.
 */
public class Server {
    public static void main(String[] args){
        new Thread(new ServerHandler(8888)).start();
    }
}
