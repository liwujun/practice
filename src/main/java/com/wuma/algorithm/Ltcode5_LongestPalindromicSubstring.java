package com.wuma.algorithm;

/**
 * Created by wuma
 * on 2016/9/22 at 14:59
 * Given a string S, find the longest palindromic substring in S.
 * You may assume that the maximum length of S is 1000,
 * and there exists one unique longest palindromic substring.
 */
public class Ltcode5_LongestPalindromicSubstring {
    public static String longestPalindrome(String s) {

        int len = s.length();
        int left = 0, right = 1;
        int[][] dp = new int[len][len];
        for (int i = 0; i < len - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = 1;
        }
        dp[len - 1][len - 1] = 1;

        for (int k = 2; k <= len; k++) {
            for (int j = 0; j <= len - k; j++) {
                if (dp[j + 1][j + k - 2] == 1 && s.charAt(j) == s.charAt(j + k - 1)) {
                    dp[j][j + k - 1] = 1;

                    if (k > left - right + 1) {
                        left = j;
                        right = j + k - 1;
                    }
                }
            }

        }
        String x = "";
        for (int i = left; i < right + 1; i++) {
            x += s.charAt(i);
        }
        return x;
    }

    public String mo(String s) {
        char[] c = new char[s.length() * 2 + 3];

        int len = c.length;
        c[0] = '$';
        c[len - 1] = '@';
        int j = 0;
        for (int i = 1; i < len - 1; i++) {
            if (i % 2 == 1) {
                c[i] = '#';
            } else {
                c[i] = s.charAt(j);
                j++;
            }
        }
        j = 0;

        int arr[] = new int[c.length];

        int center = 0, mirr = 0;
        int R = 0;

        for (int i = 1; i < c.length - 1; i++) {
            mirr = 2 * center - i;

            if (i < R) {
                arr[i] = Math.min(R - i, arr[mirr]);
            }

            while (c[i + (1 + arr[i])] == c[i - (1 + arr[i])]) {
                arr[i]++;
            }

            if (i + arr[i] > R) {
                center = i;
                R = i + arr[i];
            }
        }

        int max = 0, index = -10;

        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
                index = i;
            }
        }

        char[] result = new char[max];
        j = 0;
        for (int i = index - (max - 1); i <= index + (max - 1); i = i + 2) {
            result[j] = c[i];
            j++;
        }
        String re = new String(result);
        return re;

    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("bb"));
    }
}
