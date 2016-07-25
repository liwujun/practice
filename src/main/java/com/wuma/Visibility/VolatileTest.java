package com.wuma.Visibility;

/**
 * Created by wuma
 * on 2016/1/21 at 15:25
 */
public class VolatileTest {
    static class MyThread extends Thread {
        private volatile boolean stop = false;

        public void stopMe() {
            stop = true;
        }

        public void run() {
            int i = 0;
            while (!stop) {
                i++;
            }
            System.out.println("stop thread "+i);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        MyThread t=new MyThread();
        t.start();
        Thread.sleep(1000);
        t.stopMe();
        Thread.sleep(1000);
    }
}
