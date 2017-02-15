package com.wuma.algorithm;

/**
 * Created by wuma
 * on 2017/2/15 at 15:27
 * Given a list, rotate the list to the right by k places,
 * where k is non-negative.
 * For example:
 * Given 1->2->3->4->5->NULL and k = 2,
 * return 4->5->1->2->3->NULL.
 */
public class Ltcode61_RotateList {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    //Time limited exceed
    public static ListNode rotateRight(ListNode head, int k) {
        ListNode ln = head;
        ListNode last = head;

        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        int len = 0;
        while (head != null) {
            len++;
            last = head;

            head = head.next;
        }
        if (k > 0) {
            k = k % len;
        }
        while (k-- > 0) {
            head = ln.next;
            ln.next = null;
            last.next = ln;
            last = last.next;
            ln = head;
        }

        return ln;
    }

    public static ListNode rotateRight1(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode ln = head;
        ListNode last = head;
        int len = 0;
        while (head != null) {
            len++;
            last = head;
            head = head.next;
        }
        System.out.println(len);

        System.out.println(k % len);
        for (int j = len - k % len - 1; j > 0; j--) //Get the i-n%i th node
            ln = ln.next;

        last.next = dummy.next;
        dummy.next = ln;
        ln.next = null;
        return dummy.next;

    }

    public static void main(String[] args) {

        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
//        l3.next = l4;
//        l4.next = l5;
//        ListNode newnode = rotateRight(l1, 2);
        ListNode newnode = rotateRight1(l1, 2000000000);//1
        newnode.hashCode();
    }
}
