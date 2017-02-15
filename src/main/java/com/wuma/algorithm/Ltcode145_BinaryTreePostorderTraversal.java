package com.wuma.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by wuma
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
    Stack<TreeNode> s1 = new Stack<TreeNode>();
    Stack<TreeNode> s2 = new Stack<TreeNode>();
    public List<Integer> postorderTraversall(TreeNode root) {
        List<Integer> li = new LinkedList<Integer>();
        if(root == null){
            return li;
        }
        traverseLeft(root,s1);

        while(!s1.isEmpty()){
            TreeNode temp=s1.peek();
            if(!s2.isEmpty() && s1.peek()==s2.peek()){
                li.add(s1.pop().val);
                s2.pop();
                continue;
            }
            if(temp.right!=null){
                s2.push(temp);
                traverseLeft(temp.right,s1);

            }else
                li.add(s1.pop().val);
        }
        return li;


    }
    public void traverseLeft(TreeNode root, Stack<TreeNode> s){
        while(root!=null){
            s.push(root);
            root=root.left;
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
