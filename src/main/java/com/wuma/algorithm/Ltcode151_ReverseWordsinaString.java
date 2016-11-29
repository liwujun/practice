package com.wuma.algorithm;

/**
 * Created by liwujun
 * on 2016/11/24 at 13:58
 * Given an input string, reverse the string word by word.
 * For example,
 * Given s = "the sky is blue",
 * return "blue is sky the".
 * Update (2015-02-12):
 * For C programmers: Try to solve it in-place in O(1) space.
 * click to show clarification.
 * Clarification:
 * What constitutes a word?
 * A sequence of non-space characters constitutes a word.
 * Could the input string contain leading or trailing spaces?
 * Yes. However, your reversed string should not contain leading or trailing spaces.
 * How about multiple spaces between two words?
 * Reduce them to a single space in the reversed string.
 */
public class Ltcode151_ReverseWordsinaString {
    public String reverseWords(String s) {

        String[] result = s.split("\\s+");
        if (result == null || result.length < 1) {
            return "";
        }
        String out = "";
        for (int i = result.length - 1; i > 0; i--) {
            out += result[i] + " ";
        }
        return (out + result[0]).trim();
    }

    public static void main(String[] args) {
        String str = "i  am a lord  hhh  ";
        String[] result = str.split("\\s+");
        for (String ss : result) {
            System.out.println("-" + ss + "-");
        }
    }
}
