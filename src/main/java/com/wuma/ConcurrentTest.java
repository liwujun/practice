package com.wuma;

/**
 * Created by liwujun
 * on 2017/2/10 at 15:00
 */
public class ConcurrentTest {
    static volatile int counter = 0;

    public static void inc() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {

        }
        counter++;
    }

    public static void main(String args[]) {

        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                public void run() {
                    ConcurrentTest.inc();
                }
            }).start();
        }
        System.out.println(ConcurrentTest.counter);
    }
}
