package com.wuma.algorithm;

/**
 * Created by wuma
 * on 2016/11/18 at 12:32
 * There are N children standing in a line. Each child is assigned a rating value.
 * <p/>
 * You are giving candies to these children subjected to the following requirements:
 * <p/>
 * Each child must have at least one candy.
 * Children with a higher rating get more candies than their neighbors.
 * What is the minimum candies you must give?
 * Ì°ÐÄËã·¨
 */
public class Ltcode135_Candy {
    public int candy(int[] ratings) {
        int len = ratings.length;
        int[] can = new int[len + 1];
        can[0] = 1;
        int min_value ;
        for (int i = 1; i < len; i++) {
            if (ratings[i] > ratings[i - 1]) {
                can[i] = can[i - 1] + 1;

            } else {
                can[i] =  1;
            }
        }

        for (int i = len - 1; i > 0; i --) {
            if (can[i] >= can[i - 1] && ratings[i] < ratings[i - 1]) {
                can[i - 1] = Math.max(can[i - 1], can[i] + 1);
            }
        }
        min_value = 0;
        for (int i = 0; i < len; i++) {
            min_value += can[i];
        }

        return min_value;
    }

    public static void main(String[] args) {
        int[] ratings = new int[]{1, 2, 2};
        Ltcode135_Candy cand = new Ltcode135_Candy();

        System.out.println(cand.candy(ratings));
    }
}
