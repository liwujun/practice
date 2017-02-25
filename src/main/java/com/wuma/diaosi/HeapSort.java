package com.wuma.diaosi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
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

    /**
     * come from hadoop-common project
     * @param s
     * @param b
     * @param i
     * @param N
     */
    private static void downHeap(final sortable s, final int b,
                                 int i, final int N) {
        for (int idx = i << 1; idx < N; idx = i << 1) {
            if (idx + 1 < N && s.compare(b + idx, b + idx + 1) < 0) {
                if (s.compare(b + i, b + idx + 1) < 0) {
                    s.swap(b + i, b + idx + 1);
                } else return;
                i = idx + 1;
            } else if (s.compare(b + i, b + idx) < 0) {
                s.swap(b + i, b + idx);
                i = idx;
            } else return;
        }
    }

    /**
     * Sort the given range of items using heap sort.
     * {@inheritDoc}
     */
    public void sort(final sortable s, final int p, final int r) {
        final int N = r - p;
        // build heap w/ reverse comparator, then write in-place from end
        final int t = Integer.highestOneBit(N);
        for (int i = t; i > 1; i >>>= 1) {
            for (int j = i >>> 1; j < i; ++j) {
                downHeap(s, p-1, j, N + 1);
            }

        }
        for (int i = r - 1; i > p; --i) {
            s.swap(p, i);
            downHeap(s, p - 1, 1, i - p + 1);
        }
    }

    interface sortable{
        public void swap(int i,int j);
        public int compare(int i,int j);
    }
    class Seq implements sortable{
        int size;
        int[] num;
        public Seq(int[] src){
            size=src.length;
            num=new int[size];
            num=Arrays.copyOf(src,size);
        }

        public void swap(int i,int j) {
            int temp=num[i];
            num[i]=num[j];
            num[j]=temp;
        }

        public int compare(int i,int j) {
            return num[i]-num[j];
        }
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
        System.out.println("二进制带符号最高位");
        System.out.println("---"+Integer.highestOneBit(100));
        System.out.println("---"+Integer.highestOneBit(200));
        System.out.println("---"+Integer.highestOneBit(300));
        System.out.println("---"+Integer.highestOneBit(400));
        System.out.println("---"+Integer.highestOneBit(-100));
        System.out.println("---"+Integer.highestOneBit(-200));
        System.out.println("-1's:"+Integer.highestOneBit(-1));
        System.out.println("-1's binary:"+Integer.toBinaryString(-1));
        System.out.println("带符号右移");
        System.out.println("---"+(-10>>>1));
        System.out.println("---"+(10>>>1));
        System.out.println("---"+(-15>>>1));
    }
}
