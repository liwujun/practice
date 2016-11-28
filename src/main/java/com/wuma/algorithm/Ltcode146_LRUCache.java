package com.wuma.algorithm;

import java.util.HashMap;

/**
 * Created by liwujun
 * on 2016/11/25 at 14:44
 * Design and implement a data structure for
 * Least Recently Used (LRU) cache. It should support
 * the following operations: get and set.
 * get(key) - Get the value (will always be positive) of
 * the key if the key exists in the cache, otherwise return -1.
 * set(key, value) - Set or insert the value if
 * the key is not already present. When the cache reached its capacity,
 * it should invalidate the least recently used item before inserting a new item.
 */
public class Ltcode146_LRUCache {

    class LRUCache {
        private class CacheNode {
            public Integer key;
            public Integer val;
            public CacheNode prev;
            public CacheNode next;
        }

        private HashMap<Integer, CacheNode> map = new HashMap<Integer, CacheNode>();
        private CacheNode listHead = new CacheNode();
        private int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            listHead.prev = listHead;
            listHead.next = listHead;
        }

        public int get(int key) {
            Integer okey = new Integer(key);
            CacheNode node = map.get(okey);
            if (node == null) {
                return -1;
            } else {
                removeNode(node);
                addFirst(node);
                return node.val.intValue();
            }

        }

        public void set(int key, int value) {
            Integer okey = new Integer(key);
            CacheNode node = map.get(okey);
            if (node != null) {
                //remove the CacheNode
                node.val = value;
                removeNode(node);
            } else {
                node = new CacheNode();
                node.key = new Integer(key);
                node.val = new Integer(value);
            }
            addFirst(node);
            map.put(okey, node);
            if (map.size() > capacity) {
                Integer least = listHead.prev.key;
                map.remove(least);
                //remove the last element in the list
                removeLastNode();
            }
        }

        private void removeNode(CacheNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void removeLastNode() {
            listHead.prev.prev.next = listHead;
            listHead.prev = listHead.prev.prev;
        }

        private void addFirst(CacheNode node) {
            node.next = listHead.next;
            node.prev = listHead;
            listHead.next.prev = node;
            listHead.next = node;
        }
    }
}
