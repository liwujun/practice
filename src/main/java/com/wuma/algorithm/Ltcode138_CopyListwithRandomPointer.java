package com.wuma.algorithm;

/**
 * Created by wuma
 * on 2016/11/25 at 14:32
 * A linked list is given such that each node
 * contains an additional random pointer
 * which could point to any node in the list or null.
 * <p/>
 * Return a deep copy of the list.
 */
public class Ltcode138_CopyListwithRandomPointer {
    /**
     * Definition for singly-linked list with a random pointer.
     */
    class RandomListNode {
        int label;
        RandomListNode next, random;

        RandomListNode(int x) {
            this.label = x;
        }
    }

    public RandomListNode copyRandomList(RandomListNode head) {
        return head;
    }
}
