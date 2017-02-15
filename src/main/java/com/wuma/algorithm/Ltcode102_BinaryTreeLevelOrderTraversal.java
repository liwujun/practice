package com.wuma.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuma
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
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if (root==null){
            return list;
        }
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        List<Integer> level = new ArrayList<Integer>();
        level.add(1);
        queue.offer(root);
        int count = 0;

        List<Integer> sub_list = new ArrayList<Integer>();

        while (!queue.isEmpty()) {

            TreeNode tn = queue.poll();

            if (count == 0) {
                sub_list.add(tn.val);
                list.add(sub_list);
                sub_list = new ArrayList<Integer>();
            } else if (level.get(count) > level.get(count -1)) {
                list.add(sub_list);
                sub_list = new ArrayList<Integer>();
            }

            TreeNode leftN = tn.left;
            int currentlevel = level.get(count);
            TreeNode rightN = tn.right;
            if (leftN != null) {
                queue.offer(leftN);
                level.add(currentlevel + 1);
                sub_list.add(leftN.val);
            }
            if (rightN != null) {
                queue.offer(rightN);
                level.add(currentlevel + 1);
                sub_list.add(rightN.val);
            }
            count++;
        }
        return list;

    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        TreeNode r1 = new TreeNode(9);
        TreeNode r2 = new TreeNode(20);
        TreeNode r3 = new TreeNode(15);
        TreeNode r4 = new TreeNode(7);
        r2.left = r3;
        r2.right = r4;
        root.left = r1;
        root.right = r2;

        List<List<Integer>> list = levelOrder(root);

        list.toString();
    }
}
