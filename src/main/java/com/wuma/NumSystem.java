package com.wuma;

/**
 * Created by user on 17/5/16.
 */
public class NumSystem {
    public static void main(String[] args){
        byte sixteenSystem=(byte)0x80;
        // 1000 0000 ԭ��
        //��24��1��0111 0000
        byte VARINT=0;
        System.out.println(sixteenSystem);
        System.out.println((int)sixteenSystem);
        //11111111111111111111111110000000
        //10000000000000000000000001111111
        //10000000000000000000000010000000=-128 Դ��
        System.out.println(sixteenSystem & 0xFF);
        //1.byte�Ĵ�СΪ8bits��int�Ĵ�СΪ32bits
        //2.java�Ķ����Ʋ��õ��ǲ�����ʽ
        //0000 0001 1
        //1000 0000 -1
        //1111 1111 -128

        System.out.println(Integer.toBinaryString(sixteenSystem));
        System.out.println(Integer.toHexString(sixteenSystem));
        System.out.println(VARINT|25);
        System.out.println(0x80);
    }
}
