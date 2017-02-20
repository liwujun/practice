package com.wuma;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wuma
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
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        calendar.add(Calendar.DATE, -11);
        String start_t = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 12);
        String end_t = sdf.format(calendar.getTime());
        System.out.println(start_t+"\n"+end_t);
    }
}
