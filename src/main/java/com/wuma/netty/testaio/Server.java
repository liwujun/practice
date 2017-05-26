package com.wuma.netty.testaio;

/**
 * Created by user on 17/5/26.
 */
public class Server {
    public static void main(String[] args){
        new Thread(new AsyncHandler(8888)).start();
    }
}
