package com.wuma.lock;

import java.util.List;
import java.util.Vector;

/**
 * Created by liwujun
 * on 2016/1/4 at 17:08
 */
public class Biased {
    public static List<Integer> numberList = new Vector<Integer>();

    //-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0 -client -Xmx512m -Xms512m
    //�������ϲ����������������ܴ�
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        int count = 0;
        int startNum = 0;
        while (count < 10000000) {
            numberList.add(startNum);
            startNum += 2;
            count++;
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }
}
