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

        List<Integer> list = new ArrayList<Integer>();
        return list;
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        postorderTraversal(treeNode);
    }
}
