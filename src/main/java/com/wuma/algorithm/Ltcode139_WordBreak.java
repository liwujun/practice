package com.wuma.algorithm;

import java.util.HashSet;
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
        boolean[] word = new boolean[len + 1];
        word[0] = true;
        for (int i = 1; i <= len; i++) {
            String subs = s.substring(0, i);
            if (wordDict.contains(subs)) {
                word[i] = true;
            }
            for (int j = len; j >= i; j--) {
                String sub = s.substring(i - 1, j);
                if (wordDict.contains(sub) && word[i - 1]) {
                    word[j] = true;
                }
            }
        }
        return word[len];
    }

    public static void main(String[] args) {
        String s = "iamastrongBoy";
        System.out.println(s.substring(1, 3));
        Set<String> wordDict = new HashSet<String>();
//        wordDict.add("leet");
//        wordDict.add("code");
        String ss = "leetcode";
        ss = "ab";
        wordDict.add("a");
        wordDict.add("b");
        Ltcode139_WordBreak wb = new Ltcode139_WordBreak();

        System.out.println(wb.wordBreak(ss, wordDict));
    }
}
