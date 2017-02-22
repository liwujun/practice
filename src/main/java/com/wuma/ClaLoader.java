package com.wuma;

import java.util.*;

/**
 * Created by wuma
 * on 2016/1/4 at 17:39
 */
public class ClaLoader {
    //-XX:+TraceClassLoading
    //-XX:+PrintGCDetails
    public static void main(String[] args) {
        int negative = -15;
        int positive = 15;
        System.out.println(negative >>> 1);//print 2147483640
        System.out.println(Integer.toBinaryString(negative));    //11111111111111111111111111110001
        System.out.println(Integer.toBinaryString(negative >>> 1));//1111111111111111111111111111000
        System.out.println(positive >>> 1);//7
        System.out.println(Integer.toBinaryString(positive >>> 1));//111
        System.out.println(1<<30);//1073741824
        System.out.println(1<<31);
        //question 2:
        //jdk7
        String s = new String("1");
        s.intern();//放到常量池，发现已经在了
        String s2 = "1";
        System.out.println(s == s2);//false s引用一个对象，s2引用常量池的地址 intern只是把它放到常量池

        String s3 = new String("1") + new String("1");
        s3.intern();//将s3中的"11"放入string常量池，jdk7在heap中
        String s4 = "11";
        System.out.println(s3 == s4);//true

        //question 3:
        String s_ = new String("1");
        String s2_ = "1";
        s_.intern();
        System.out.println(s_ == s2_);//false 同第一段，inter位置改变无影响

        String s3_ = new String("1") + new String("1"); //在堆中生成了一个对象
        String s4_ = "11";
        s3_.intern();
        System.out.println(s3_ == s4_);//false
        StringBuffer sb = new StringBuffer();
        StringBuilder sber = new StringBuilder();
        HashMap<String, Object> h = new HashMap<String, Object>();
        TreeMap treeMap = new TreeMap();
        Queue<Map> queue = new PriorityQueue<Map>();

        System.out.println("--------");
        String str=new String("abc");
        String str2="abc";
        System.out.println("str.equals(str2): "+str.equals(str2)+"\nstr==str2: "+(str==str2));
        System.out.println("str.hashcode:"+str.hashCode()+" str2.hashcode:"+str2.hashCode());

        Integer i1 = 27;
        Integer i2 = 27;
        Integer i3 = 2787;
        Integer i4 = 2787;
        System.out.println(i1 == i2);
        System.out.println(i3 == i4);

        System.out.println("ja"+"va"=="java");

    }

}
