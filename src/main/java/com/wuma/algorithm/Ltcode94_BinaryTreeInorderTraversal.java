package com.wuma.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by wuma
 * on 2017/2/15 at 17:48
 * Given a binary tree, return the inorder traversal of its nodes' values.
 * <p/>
 * For example:
 * Given binary tree [1,null,2,3],
 * 1
 * \
 * 2
 * /
 * 3
 * return [1,3,2].
 * <p/>
 * Note: Recursive solution is trivial, could you do it iteratively?
 */
public class Ltcode94_BinaryTreeInorderTraversal {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list=new ArrayList<Integer>();
        Stack<TreeNode> s1=new Stack<TreeNode>();

        traversalLeft(root,s1);
        while (!s1.empty()){
            TreeNode temp=s1.peek();

            if (temp.right!=null){
                list.add(s1.pop().val);
                traversalLeft(temp.right,s1);
            }else {
                list.add(s1.pop().val);
            }
        }
        return list;
    }

    public void traversalLeft(TreeNode tn,Stack s){
        while (tn!=null){
         s.push(tn);
            tn=tn.left;
        }
    }

    public static void main(String args[]){
        TreeNode tn1=new TreeNode(1);
        TreeNode tn2=new TreeNode(2);
        TreeNode tn3=new TreeNode(3);
        tn1.right=tn2;
        tn2.left=tn3;
        Ltcode94_BinaryTreeInorderTraversal lc=new Ltcode94_BinaryTreeInorderTraversal();
        List<Integer> list=lc.inorderTraversal(tn1);
        System.out.println(list.toArray());

    }
}
