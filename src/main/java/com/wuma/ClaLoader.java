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
        //question 2:
        //jdk7
        String s = new String("1");
        s.intern();//�ŵ������أ������Ѿ�����
        String s2 = "1";
        System.out.println(s == s2);//false s����һ������s2���ó����صĵ�ַ internֻ�ǰ����ŵ�������

        String s3 = new String("1") + new String("1");
        s3.intern();//��s3�е�"11"����string�����أ�jdk7��heap��
        String s4 = "11";
        System.out.println(s3 == s4);//true

        //question 3:
        String s_ = new String("1");
        String s2_ = "1";
        s_.intern();
        System.out.println(s_ == s2_);//false ͬ��һ�Σ�interλ�øı���Ӱ��

        String s3_ = new String("1") + new String("1"); //�ڶ���������һ������
        String s4_ = "11";
        s3_.intern();
        System.out.println(s3_ == s4_);//false
    }

}
