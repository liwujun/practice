package com.wuma.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwujun
 * on 2016/11/7 at 16:31
 * Given a binary tree, return the postorder traversal of its nodes' values.
 * <p/>
 * For example:
 * Given binary tree {1,#,2,3},
 * 1
 * \
 * 2
 * /
 * 3
 * return [3,2,1].
 * <p/>
 * Note: Recursive solution is trivial, could you do it iteratively?
 * link: https://leetcode.com/problems/binary-tree-postorder-traversal/
 */
public class Ltcode145_BinaryTreePostorderTraversal {
    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static List<Integer> postorderTraversal(TreeNode root) {

        if (root == null) {
            return new ArrayList<Integer>();
        }
        List<Integer> list = new ArrayList<Integer>();

        TreeNode left = root.left;
        TreeNode right = root.right;
        List<Integer> left_int = postorderTraversal(left);
        List<Integer> right_int = postorderTraversal(right);
        if (left_int != null) {
            list.addAll(left_int);
        }
        if (right_int != null) {
            list.addAll(right_int);
        }
        int value = root.val;
        list.add(value);
        return list;
    }


    public static void main(String[] args) {
        TreeNode right = new TreeNode(2);
        TreeNode left = new TreeNode(3);
        right.left = left;

        TreeNode treeNode = new TreeNode(1);
        treeNode.right = right;
        List<Integer> list = postorderTraversal(treeNode);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
