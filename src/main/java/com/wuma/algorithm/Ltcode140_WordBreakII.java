package com.wuma.algorithm;

import java.util.*;

/**
 * Created by wuma
 * on 2016/11/7 at 17:55
 * Given a string s and a dictionary of words dict,
 * add spaces in s to construct a sentence
 * where each word is a valid dictionary word.
 * Return all such possible sentences.
 * For example, given
 * s = "catsanddog",
 * dict = ["cat", "cats", "and", "sand", "dog"].
 * A solution is ["cats and dog", "cat sand dog"].
 */
public class Ltcode140_WordBreakII {
    public List<String> wordBreak(String s, Set<String> wordDict) {
        List<String> res = new ArrayList<String>();
        int len = s.length();
        boolean[] word = new boolean[len + 1];
        word[0] = true;
        int max_length = 0;
        for (String str : wordDict) {
            max_length = Math.max(max_length, str.length());
        }
        for (int i = 1; i <= len; i++) {

            for (int j = Math.max(0, i - max_length); j < i; j++) {
                if (word[j] && wordDict.contains(s.substring(j, i))) {
                    word[i] = true;
                    break;
                }
            }
        }
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        return search_str(s, wordDict, map, word);
    }

    List<String> search_str(String s, Set<String> wordDict,
                            HashMap<String, List<String>> map, boolean[] word) {
        if (map.containsKey(s)) {
            return map.get(s);
        }
        List<String> res = new ArrayList<String>();
        if (s.length() == 0) {
            return res;
        }
        if (wordDict.contains(s)) {
            res.add(s);
        }
        int end = s.length();
        for (int i = end - 1; i >= 0; i--) {
            String cur = s.substring(i, end);
            if (wordDict.contains(cur) && word[i]) {
                List<String> list = search_str(s.substring(0, i), wordDict, map, word);
                for (String str : list) {
                    res.add(str + " " + cur);
                }
            }
        }
        map.put(s, res);
        return res;
    }


    public static void main(String[] args) {
        Set<String> wordDict = new HashSet<String>();
        String ss = "catsanddog";
        wordDict.add("cats");
        wordDict.add("cat");
        wordDict.add("and");
        wordDict.add("sand");
        wordDict.add("dog");
        Ltcode140_WordBreakII wb = new Ltcode140_WordBreakII();

        List<String> res = wb.wordBreak(ss, wordDict);
        for (String r : res) {
            System.out.println(r);
        }
    }
}
