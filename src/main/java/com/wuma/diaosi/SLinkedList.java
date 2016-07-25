package com.wuma.diaosi;

/**
 * Created by wuma
 * on 2016/7/25 at 14:55
 */
public class SLinkedList {
    static class Node<T> {
        Node next;
        T data;
    }

    Node NonrecursionReverse(Node current) {
        //initialization
        Node previousNode = null;
        Node nextNode = null;
        while (current != null) {
            //save the next node
            nextNode = current.next;
            //
            current.next = previousNode;
            previousNode = current;
            current = nextNode;
        }
        return previousNode;
    }

    Node RecursionReverse(Node current) {
        if (current == null || current.next == null) return current;
        Node nextNode = current.next;
        current.next = null;
        Node reverseNode = RecursionReverse(nextNode);
        nextNode.next = current;
        return reverseNode;
    }

    public static void main(String[] args) {
        Node<Integer> head = new Node<Integer>();
        head.data = -1;
        Node curr = head;
        for (int i = 0; i < 10; i++) {
            Node<Integer> n = new Node<Integer>();
            n.data = i;
            curr.next = n;
            curr = n;
        }
        Node nnode = head;
        while (nnode != null) {
            System.out.print(nnode.data + " ");
            nnode = nnode.next;
        }
        System.out.println("reverse: ");
        SLinkedList s = new SLinkedList();
        head = s.RecursionReverse(head);
        Node nnnode = head;
        while (nnnode != null) {
            System.out.print(nnnode.data + " ");
            nnnode = nnnode.next;
        }
    }
}
