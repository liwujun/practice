package com.wuma.algorithm;

/**
 * Created by wuma
 * on 2016/9/22 at 14:23
 * Given a string, find the length of the longest substring without repeating characters.
 * <p/>
 * Examples:
 * <p/>
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * <p/>
 * Given "bbbbb", the answer is "b", with the length of 1.
 * <p/>
 * Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * <p/>
 * Subscribe to see which companies asked this question
 * <p/>
 * Show Tags
 * Show Similar Problems
 */
public class Ltcode3_LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int len = s.length();
        int[] dp = new int[len];
        int maxNoRepeatingLen = 1;
        int maxindex = 0;
        int last_start = 0;
        dp[0] = 1;
        for (int i = 1; i < len; i++) {
            for (int j = i - 1; j >= last_start; j--) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i] = i - j;
                    last_start = j + 1;
                    break;
                } else if (j == last_start) {
                    dp[i] = dp[i - 1] + 1;
                }
            }
            if (dp[i] > maxNoRepeatingLen) {
                maxNoRepeatingLen = dp[i];
                maxindex = i + 1 - maxNoRepeatingLen;
            }
        }
        return maxNoRepeatingLen ;
    }
}
