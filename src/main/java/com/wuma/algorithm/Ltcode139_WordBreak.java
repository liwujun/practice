package com.wuma.algorithm;

import java.util.Set;

/**
 * Created by liwujun
 * on 2016/11/25 at 15:05
 * Given a string s and a dictionary of words dict,
 * determine if s can be segmented into a space-separated
 * sequence of one or more dictionary words.
 * For example, given
 * s = "leetcode",
 * dict = ["leet", "code"].
 * Return true because "leetcode" can be segmented as "leet code".
 */
public class Ltcode139_WordBreak {
    public boolean wordBreak(String s, Set<String> wordDict) {
        int len = s.length();
        int begin = 0;
        for (int i = 0; i < len; i++) {
            String s1 = s.substring(begin, i + 1);
            if (wordDict.contains(s1)) {
                begin = i ;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "iamastrongBoy";
        System.out.println(s.substring(1, 3));
    }
}
