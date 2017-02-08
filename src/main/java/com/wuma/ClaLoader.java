package com.wuma;

/**
 * Created by wuma
 * on 2016/1/4 at 17:39
 */
public class ClaLoader {
    //-XX:+TraceClassLoading
    //-XX:+PrintGCDetails
    public static void main(String[] args){
        int negative=-15;
        int positive=15;
        System.out.println(negative>>>1);//print 2147483640
        System.out.println(Integer.toBinaryString(negative));    //11111111111111111111111111110001
        System.out.println(Integer.toBinaryString(negative>>>1));//1111111111111111111111111111000
        System.out.println(positive>>>1);//7
        System.out.println(Integer.toBinaryString(positive>>>1));//111
    }

}
