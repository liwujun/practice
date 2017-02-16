package com.wuma.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by wuma
 * on 2017/2/15 at 18:12
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
 * <p/>
 * Note:
 * You may assume k is always valid, 1 ¡Ü k ¡Ü BST's total elements.
 * <p/>
 * Follow up:
 * What if the BST is modified (insert/delete operations)
 * often and you need to find the kth smallest frequently? How would you optimize the kthSmallest routine?
 * <p/>
 * Show Hint
 */
public class Ltcode230_KthSmallestElementinaBST {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int kthSmallest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<Integer>();
        traversal(root, k, list);
        return list.get(k - 1);
    }

    public void traversal(TreeNode tn, int k, List list) {
        if (tn == null)
            return;
        traversal(tn.left, k, list);
        list.add(tn.val);
        traversal(tn.right, k, list);
    }

    public int kthSmallestNoRecursive(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        Stack<TreeNode> stack1 = new Stack<TreeNode>();
        stack.push(root);
        while (!stack.isEmpty()) {
            traversalNoRecursive(root, stack);
            if (stack.peek().right == null) {
                stack1.push(stack.pop());
            } else {
                traversalNoRecursive(stack.peek().right, stack);
            }
            if (stack1.size() == k)
                break;
        }
        return stack1.peek().val;
    }

    public void traversalNoRecursive(TreeNode tn, Stack s) {
        while (tn.left != null) {
            s.push(tn.left);
            tn = tn.left;
        }
    }

    public static void main(String args[]) {
        TreeNode tn = new TreeNode(3);
        TreeNode tn1 = new TreeNode(2);
        TreeNode tn2 = new TreeNode(1);
        TreeNode tn3 = new TreeNode(4);
//        tn.left = tn1;
//        tn.right = tn3;
//        tn1.left = tn2;
        Ltcode230_KthSmallestElementinaBST bst = new Ltcode230_KthSmallestElementinaBST();
//        bst.kthSmallest(tn, 1);
        System.out.println(bst.kthSmallestNoRecursive(tn,1));
    }
}

