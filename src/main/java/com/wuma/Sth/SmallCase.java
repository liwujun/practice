package com.wuma.Sth;

/**
 * Created by liwujun
 * on 2017/2/10 at 17:29
 */
public class SmallCase {
    public static void main(String args[]){
        int a=10;
        int b=10;
        int[] aa=new int[1];
        method(a,b);
        methodb(aa);
        System.out.println("a="+a+",b="+b);
        System.out.println(""+aa[0]);
    }
    static void method( Integer a,Integer b){

        int c=a.intValue();
        c=200;
        a.intValue();

    }

    static int methodc(Integer a){
        return 200;
    }
    static void methodb(int[] aa){
        aa[0]=1;

    }
}
