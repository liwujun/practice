package com.wuma.diaosi;

import sun.misc.Compare;
import sun.misc.Sort;

import java.util.Stack;

/**
 * Created by wuma
 * on 2016/7/25 at 11:12
 */
public class QSort {

    int partition(int[] arr, int low, int high) {
        System.out.println("low:" + low + " high:" + high);
        System.out.println("arr:");
        for (int i = low; i <= high; i++) {
            System.out.print(arr[i] + " ");
        }

        int pivot = arr[low];
        while (low < high) {

            while (high > low && arr[high] >= pivot) high--;
            arr[low] = arr[high];
            while (high > low && arr[low] <= pivot) low++;
            arr[high] = arr[low];

        }
        arr[low] = pivot;
        return low;
    }

    void QS(int[] arr, int low, int high) {
        if (low < high) {
            int h = partition(arr, low, high);
            System.out.println("partition h:" + h);
            QS(arr, low, h - 1);
            QS(arr, h + 1, high);
        }
    }

    void NonrecursionQS(int[] arr, int low, int high) {
        Stack<Integer> st = new Stack<Integer>();
        if (low < high) {
            int h = partition(arr, low, high);
            if (low < h - 1) {
                st.push(low);
                st.push(h - 1);
            }
            if (h + 1 < high) {
                st.push(h + 1);
                st.push(high);
            }
            while (!st.empty()) {
                int q = st.pop();
                int p = st.pop();
                h = partition(arr, p, q);
                if (p < h - 1) {
                    st.push(p);
                    st.push(h - 1);
                }
                if (h + 1 < q) {
                    st.push(h + 1);
                    st.push(q);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{10, 1, 9, 2, 4, 6, 8, 7, 7, 100, 23, 33, 26, 3, 20};
        Integer[] arrayObj = new Integer[]{10, 1, 9, 2, 4, 6, 8, 7, 7, 100, 23, 33, 26, 3, 20};
        array = new int[]{10, 1, 9, 2, 4};
        QSort qSort = new QSort();
//        qSort.QS(array, 0, array.length - 1);
        qSort.NonrecursionQS(array, 0, array.length - 1);
        Sort.quicksort(arrayObj, new Compare() {
            public int doCompare(Object o, Object o1) {
                return (Integer) o - (Integer) o1;
            }
        });
        System.out.println("---");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("----objInteger");
        for (int i = 0; i < arrayObj.length; i++) {
            System.out.print(arrayObj[i] + " ");
        }
    }
}
