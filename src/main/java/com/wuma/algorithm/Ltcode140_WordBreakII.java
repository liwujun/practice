package com.wuma.algorithm;

import java.util.*;

/**
 * Created by liwujun
 * on 2016/11/7 at 17:55
 * Given a string s and a dictionary of words dict,
 * add spaces in s to construct a sentence
 * where each word is a valid dictionary word.
 * Return all such possible sentences.
 * For example, given
 * s = "catsanddog",
 * dict = ["cat", "cats", "and", "sand", "dog"].
 * A solution is ["cats and dog", "cat sand dog"].
 */
public class Ltcode140_WordBreakII {
    public List<String> wordBreak(String s, Set<String> wordDict) {
        Trie trie = new Trie();
        Iterator iterator = wordDict.iterator();
        while (iterator.hasNext()) {
            String singlew = (String) iterator.next();
            trie.insert(singlew);
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {

        }
        return new ArrayList<String>();
    }

    class TrieNode {
        char c;
        boolean leaf;
        Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();

        public TrieNode(char c) {
            this.c = c;
        }

        public TrieNode() {

        }
    }

    class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            Map<Character, TrieNode> children = root.children;
            int len = word.length();
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                TrieNode t;
                if (children.containsKey(ch)) {
                    t = children.get(ch);
                } else {
                    t = new TrieNode(ch);
                    children.put(ch, t);
                }
                children = t.children;
                if (i == len - 1)
                    t.leaf = true;
            }
        }

        public boolean search(String word) {
            TrieNode t = searchNode(word);
            return t.leaf && t != null;
        }

        public boolean startsWith(String prefix) {
            return searchNode(prefix) != null;
        }

        public TrieNode searchNode(String word) {
            Map<Character, TrieNode> children = root.children;
            TrieNode t = null;
            int len = word.length();
            for (int i = 0; i < len; i++) {
                char c = word.charAt(i);
                if (!children.containsKey(c))
                    return null;
                t = children.get(c);
                children = t.children;
            }
            return t;
        }
    }
}
