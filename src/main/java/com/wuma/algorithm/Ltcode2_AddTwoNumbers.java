package com.wuma.algorithm;

/**
 * Created by liwujun
 * on 2016/9/22 at 11:04
 */
public class Ltcode2_AddTwoNumbers {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode l3 = null;
        ListNode l4 = null;
        int digit2 = 0;
        while (l1 != null || l2 != null || digit2 != 0) {
            int result = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val);
            int digit1 = (result + digit2) % 10;
            digit2 = (result + digit2) / 10;
            ListNode ll = new ListNode(digit1);
            if (l3 == null) {
                l3 = ll;
                l4 = l3;
            } else {
                l3.next = ll;
            }
            if (l3.next != null) {
                l3 = l3.next;
            }
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        return l4;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(4);
        listNode1.next = listNode2;
        ListNode listNode3 = new ListNode(3);
        listNode2.next = listNode3;

        ListNode listNode4 = new ListNode(5);
        ListNode listNode5 = new ListNode(6);
        listNode4.next = listNode5;
        ListNode listNode6 = new ListNode(4);
        listNode5.next = listNode6;
        ListNode listNode7 = listNode1;
//        while (listNode7 != null) {
//            System.out.println(listNode7.val);
//            listNode7 = listNode7.next;
//        }
        Ltcode2_AddTwoNumbers ltcode2_addTwoNumbers = new Ltcode2_AddTwoNumbers();
        ListNode ln = ltcode2_addTwoNumbers.addTwoNumbers(listNode1, listNode4);
        while (ln != null) {
            System.out.println(ln.val);
            ln = ln.next;
        }
    }
}
