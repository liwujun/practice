package com.wuma.algorithm;

/**
 * Created by liwujun
 * on 2016/9/22 at 15:04
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * <p/>
 * Find the median of the two sorted arrays.
 * The overall run time complexity should be O(log (m+n)).
 * <p/>
 * Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * <p/>
 * The median is 2.0
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * <p/>
 * The median is (2 + 3)/2 = 2.5
 */
public class Ltcode4_MedianofTwoSortedArrays {
    //o (m+n)
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double d = 1.0;
        int len = nums1.length + nums2.length;
        int[] merge = new int[len];
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        while (p1 < nums1.length && p2 < nums2.length && p3 < len) {
            if (nums1[p1] > nums2[p2]) {
                merge[p3] = nums2[p2];
                p3++;
                p2++;
            } else if (nums1[p1] < nums2[p2]) {
                merge[p3] = nums1[p1];
                p3++;
                p1++;
            } else {
                merge[p3] = nums1[p1];
                p3++;
                p1++;
                merge[p3] = nums2[p2];
                p2++;
                p3++;
            }
        }
        while (p1 < nums1.length) {
            merge[p3++] = nums1[p1++];
        }
        while (p2 < nums2.length) {
            merge[p3++] = nums2[p2++];
        }
        if (len % 2 == 1) {
            d = merge[len / 2] / 1.0;
        } else {
            d = (merge[len / 2 - 1] + merge[len / 2]) / 2.0;
        }

        return d;
    }

    //o log(m+n)
    public double findMedianSortedArraysLogmaddn(int[] nums1, int[] nums2) {
        double t = 1;
        int m=nums1.length;
        int n=nums2.length;
        int total=m+n;
        if ((total & 0x1)>0){
            return find_kth(nums1,1,m,nums2,1,n,total/2+1);
        }else {
            return (find_kth(nums1,1,m,nums2,1,n,total/2)+find_kth(
                    nums1,1,m,nums2,1,n,total/2+1
            ))/2;
        }
    }

    int find_kth(int[] nums1,int m,int m1,int[] nums2,int n,int n1,int k){
        if (m>n) return find_kth(nums2,n,n1,nums1,m,m1,k);
        if (m==0) return nums2[k-1];
        if (k==1) return Math.min(nums1[m],nums2[n]);
        int a=Math.min(k/2,m),b=k-a;
        if (nums1[a-1]<nums2[b-1])
            return find_kth(nums1,a,m-a,nums2,1,n,k-a);
        else if (nums1[a-1]>nums2[b-1])
            return find_kth(nums1,1,m,nums2,b,n-b,k-b);
        else
            return nums1[a-1];

    }

    public static void main(String[] args) {
        int num1[] = new int[]{1, 2};
        int num2[] = new int[]{3, 4};
        Ltcode4_MedianofTwoSortedArrays l = new Ltcode4_MedianofTwoSortedArrays();
        double x = l.findMedianSortedArrays(num1, num2);
        System.out.println(x);
        System.out.println(10&0x1);
        System.out.println(11&0x1);
        System.out.println(-11&0x1);
        System.out.println(-10&0x1);
    }
}
