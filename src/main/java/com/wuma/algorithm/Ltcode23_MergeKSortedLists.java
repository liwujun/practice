package com.wuma.algorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by liwujun
 * on 2016/10/31 at 10:16
 * Merge k sorted linked lists
 * and return it as one sorted list.
 * Analyze and describe its complexity.
 */
public class Ltcode23_MergeKSortedLists {

    /**
     * Definition for singly-linked list.
     **/
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {

        int k = lists.length;
        if (k == 0) {
            return null;
        }
        if (k == 1) {
            return lists[0];
        }
        PriorityQueue<ListNode> heap = new PriorityQueue<ListNode>(2,
                new Comparator<ListNode>() {
                    public int compare(ListNode o1, ListNode o2) {
                        return o1.val - o2.val;
                    }
                });
        for (ListNode node : lists) {
            if (node != null) {
                heap.add(node);
            }
        }
        ListNode head = null;
        ListNode cur = null;
        while (!heap.isEmpty()) {
            ListNode node = heap.poll();
            if (node.next != null) {
                heap.add(node.next);
            }
            if (head == null) {
                head = node;
                cur = node;
            } else {
                cur.next = node;
                cur = cur.next;
            }
        }
        return head;
    }
    public static void main(String[] args){

        int size=-10;
        System.out.println(size>>>1);
        System.out.println(size>>1);
    }
}
