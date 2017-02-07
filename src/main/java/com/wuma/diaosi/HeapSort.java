package com.wuma.diaosi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Created by zc
 * on 2016/11/29 at 11:43
 */
public class HeapSort {
    // use min heap to implements
    PriorityQueue<Integer> minheap = new PriorityQueue<Integer>();

    static int[] hs(BufferedReader stdin, PrintStream stdout) throws IOException {
        int count = 0;
        Stack<String> numStack = new Stack<String>();
        while (true) {
            stdout.print("Enter a number or leave a blank to finish:");
            String str = stdin.readLine();
            if (str.length() == 0) {
                break;
            }
            numStack.push(str);
            count++;
        }
        int[] nums = new int[count];
        count = 0;
        while (!numStack.isEmpty()) {
            nums[count++] = Integer.valueOf(numStack.pop());
        }

        return createMinHeap(nums);
    }


    /**
     * produce a heap
     *
     * @param arr
     * @return
     */
    static int[] createMinHeap(int[] arr) {

        int len = arr.length;
        for (int k = 1; k < len; k++) {
            int child = k;
            int parent = (k - 1) / 2;
            int temp = arr[k];
            while (parent >= 0 && child != 0 && arr[parent] > temp) {
//                arr[parent]=temp;
                //wrong anwser
                child = child;
                arr[child] = arr[parent];
                child = parent;
                parent = (parent - 1) / 2;
            }
            arr[child] = temp;
        }
        return arr;
    }

    static int[] adjustMinHeap(int[] arr) {
        int len = arr.length;
        int[] deleteoneheap = new int[len - 1];
        for (int k = 1; k < len; k++) {
            deleteoneheap[k - 1] = arr[k];
        }
        deleteoneheap = createMinHeap(deleteoneheap);
        return deleteoneheap;
    }

    public static void main(String[] args) throws Exception {
        int[] xx = new HeapSort().hs(new BufferedReader(new InputStreamReader(System.in)), System.out);
        for (int x : xx) {
            System.out.print(x + " ");
        }
        System.out.println();
        while (xx != null && xx.length != 0) {
            System.out.print(xx[0]+" ");
            xx = adjustMinHeap(xx);
        }
    }
}
