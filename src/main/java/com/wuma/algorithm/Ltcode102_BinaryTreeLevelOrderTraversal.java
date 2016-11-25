package com.wuma.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwujun
 * on 2016/11/25 at 14:47
 * Given a binary tree, return the level order traversal
 * of its nodes' values. (ie, from left to right, level by level).
 * <p/>
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * return its level order traversal as:
 * [
 * [3],
 * [9,20],
 * [15,7]
 * ]
 */
public class Ltcode102_BinaryTreeLevelOrderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        return new ArrayList<List<Integer>>();
    }
}
