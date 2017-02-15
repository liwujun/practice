package com.wuma.algorithm;

import java.util.Arrays;

/**
 * Created by wuma
 * on 2016/11/18 at 12:38
 * Given an array of citations
 * (each citation is a non-negative integer) of a
 * researcher, write a function to compute the researcher's h-index.
 * <p/>
 * According to the definition of h-index on Wikipedia:
 * "A scientist has index h if h of his/her N papers have
 * at least h citations each, and the other N - h papers have no more than h citations each."
 * <p/>
 * For example, given citations = [3, 0, 6, 1, 5],
 * which means the researcher has 5 papers in total and each
 * of them had received 3, 0, 6, 1, 5 citations respectively.
 * Since the researcher has 3 papers with at least 3 citations
 * each and the remaining two with no more than 3 citations each, his h-index is 3.
 * <p/>
 * Note: If there are several possible values for h, the maximum one is taken as the h-index.
 */
public class Ltcode274_HIndex {
    public int hIndex(int[] citations) {
        if (citations.length <= 0) {
            return citations.length;
        }
        Arrays.sort(citations);

        int h = 0;
        int len = citations.length;
        for (int i = 0; i < len; i++) {

            if (i == len - 1) {
                if (citations[len - i - 1] >= i + 1) {
                    h = i + 1;
                }
            }  else {
                if ((citations[len - i - 1] >= i + 1)
                        && (citations[len - i - 2] <= i + 1)) {
                    h = i + 1;
                }
            }
        }
        return h;
    }

    public static void main(String[] args) {
        int[] tobe = new int[]{3, 0, 6, 1, 5};
        tobe = new int[]{11, 15};
        tobe = new int[]{0};
        tobe = new int[]{0, 1};
        tobe = new int[]{1, 1};
        Ltcode274_HIndex hindex = new Ltcode274_HIndex();
        int h = hindex.hIndex(tobe);
        System.out.println("\n" + h);
    }
}
