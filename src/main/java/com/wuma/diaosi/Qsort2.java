package com.wuma.diaosi;

/**
 * Created by liwujun
 * on 2016/11/17 at 10:12
 */
public class Qsort2 {

    int partition(int[] b, int low, int high) {
        if (high > low) {
            return low;
        }
        int pivotv = b[low];
        while (low < high) {
            while (pivotv <= b[high])
                high--;
            b[low] = b[high];
            while (pivotv >= b[low])
                low++;
            b[high] = b[low];
        }
        b[low] = pivotv;
        return low;
    }

    int[] qsort(int[] a, int begin, int end) {
        if (begin >= end) {
            return a;
        }
        int pivot = partition(a, begin, end);
        qsort(a, begin, pivot - 1);
        qsort(a, pivot + 1, end);
        return a;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 10, 198, 2, 3, 44, 56, 10, 23, 1, 15, 63, 2};
    }
}
