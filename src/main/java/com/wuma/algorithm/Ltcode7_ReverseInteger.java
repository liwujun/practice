package com.wuma.algorithm;

import java.math.BigInteger;

/**
 * Created by liwujun
 * on 2016/10/26 at 11:09
 * Reverse digits of an integer.
 * <p/>
 * Example1: x = 123, return 321
 * Example2: x = -123, return -321
 * links:https://leetcode.com/problems/reverse-integer/
 */
public class Ltcode7_ReverseInteger {
    /**
     * ��-2^32������2^32-1��
     * ��-2147483648��                  ����2147483647������һǧ�İ���ʮ�İ�����Ǫ������ʮ��
     * ��ʮһ����Ǫ�����ʮ������Ǫ������ʮ�� ��ʮһ����Ǫ�����ʮ������Ǫ������ʮ��
     * 1534236469 ���½�������� 1056389759
     * Ӧ���� 9646324351
     *
     * @param x
     * @return
     */
    public static int reverse1(int x) {

        int result = 0;
        while (x != 0) {
            result = result * 10;
            result += x % 10;
            x = x / 10;
            System.out.println(result);
        }
        return result;
    }

    public static int reverse(int x) {

        boolean negative = x < 0 ? true : false;
        if (negative) {
            x = -x;
        }
        StringBuilder sb = new StringBuilder();
        while (x != 0) {
            sb.append((x % 10) + "");
            x = x / 10;
        }
        try {
            return negative ? -Integer.valueOf(sb.toString())
                    : Integer.valueOf(sb.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(reverse(1534236469));
    }
}
