package com.wuma.algorithm;

/**
 * Created by wuma
 * on 2016/11/24 at 14:12
 * Serialization is the process of converting a data structure or
 * object into a sequence of bits so that it can be stored in a file or memory buffer,
 * or transmitted across a network connection link to be reconstructed
 * later in the same or another computer environment.
 * Design an algorithm to serialize and deserialize a binary tree.
 * There is no restriction on how your serialization/deserialization
 * algorithm should work. You just need to ensure that a binary tree
 * can be serialized to a string and this string can be deserialized
 * to the original tree structure.
 * For example, you may serialize the following tree
 * 1
 * / \
 * 2   3
 * / \
 * 4   5
 * as "[1,2,3,null,null,4,5]", just the same as how
 * LeetCode OJ serializes a binary tree. You do not necessarily need to
 * follow this format, so please be creative and come up with different approaches yourself.
 * Note: Do not use class member/global/static variables to store states.
 * Your serialize and deserialize algorithms should be stateless.
 */
public class Ltcode297_SerializeandDeserializeBinaryTree {
    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return "";
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        return new TreeNode(1);
    }
    // Your Codec object will be instantiated and called as such:
    // Codec codec = new Codec();
    // codec.deserialize(codec.serialize(root));

}
