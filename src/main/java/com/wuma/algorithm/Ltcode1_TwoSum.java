package com.wuma.algorithm;

import java.util.Arrays;

/**
 * Created by liwujun
 * on 2016/9/22 at 10:14
 */
public class Ltcode1_TwoSum {

    public int[] twoSum(int[] nums, int target) {
        int[] sorted = new int[nums.length];
        System.arraycopy(nums, 0, sorted, 0, nums.length);
        Arrays.sort(sorted);

        int i = 0, j = nums.length - 1;
        while (i < j) {
            if (sorted[i] + sorted[j] > target) {
                j--;
            } else if (sorted[i] + sorted[j] < target) {
                i++;
            } else {
                break;
            }
        }
        int x = sorted[i];
        int y = sorted[j];
        i = -1;
        j = -1;
        for (int k = 0; k < nums.length; k++) {
            if (nums[k] == x || nums[k] == y) {
                if (i == -1) {
                    i = k;
                } else {
                    j = k;
                }
            }
        }
        int[] result = new int[2];
        result[0] = i;
        result[1] = j;
        return result;
    }

}
