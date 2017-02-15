package com.wuma.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by wuma
 * on 2016/10/24 at 11:05
 * https://leetcode.com/problems/zigzag-conversion/
 * The string "PAYPALISHIRING" is written in a zigzag pattern on
 * a given number of rows like this: (you may want to display
 * this pattern in a fixed font for better legibility)
 * <p/>
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * And then read line by line: "PAHNAPLSIIGYIR"
 * Write the code that will take a string and make this conversion given a number of rows:
 * <p/>
 * string convert(string text, int nRows);
 * convert("PAYPALISHIRING", 3) should return "PAHNAPLSIIGYIR"
 */
public class Ltcode6_ZigZagConversion {
    public static String convert(String s, int numRows) {
        if (numRows <= 1) {
            return s;
        }
        char[] chars = s.toCharArray();
        int[] next = new int[numRows * 2 - 2];
        for (int i = 0; i < numRows; i++) {
            next[i] = i + 1;
        }
        int max = numRows;
        for (int i = numRows; i < numRows * 2 - 2; i++) {
            next[i] = --max;
        }
        //next is status-convert table
        Map<Integer, String> map = new HashMap<Integer, String>();
        int cycle = numRows * 2 - 2;
        for (int i = 0; i < chars.length; i++) {
            char sin = chars[i];
            int key = next[i % cycle];
            if (map.containsKey(key)) {
                String arraychar = map.get(key);
                arraychar += sin;
                map.remove(key);
                map.put(key, arraychar);
            } else {
                map.put(key, sin + "");
            }
        }
        String re = "";
        for (int i = 0; i < numRows; i++) {
            if (map.containsKey(next[i])) {
                re += map.get(next[i]);
            }
        }
        return re;
    }

    public static void main(String[] args) {
        System.out.println(convert("P", 1));
    }
}
