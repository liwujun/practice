package com.wuma.algorithm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 实现一个缓存容器，支持容量指定和超出容量按照热度淘汰，同时尽可能保证多线程环境的读写性能，不能使用外部三方库
 * 编写要附带测试case
 * Created by wu on 2017/7/25.
 * mailto: @126.com
 */
public class CacheContainer {

    private class CacheNode {
        public Integer key;
        public Integer val;
        public CacheNode prev;
        public CacheNode next;
    }

    private ConcurrentHashMap<Integer, CacheNode> map = new ConcurrentHashMap<Integer, CacheNode>();
    private CacheNode listHead = new CacheNode();
    //容器的容量
    private int capacity;
    //容器现在的大小
    volatile int size;
    private ReadWriteLock cacheLock;

    public CacheContainer(int capacity) {
        this.capacity = capacity;
        listHead.prev = listHead;
        listHead.next = listHead;
        this.size = 0;
        //创建一个读写锁，提高并发读的性能
        cacheLock = new ReentrantReadWriteLock(false);
    }

    public int get(int key) {
        //当有操作写节点的时候，读被限制
        cacheLock.readLock().lock();
        Integer iKey = new Integer(key);
        CacheNode node = map.get(iKey);
        cacheLock.readLock().unlock();

        if (node == null) {
            return -1;
        } else {
            removeNode(node);
            addFirst(node);
            return node.val.intValue();
        }
    }

    public void set(int key, int value) {
        cacheLock.writeLock().lock();
        Integer okey = new Integer(key);
        CacheNode node = map.get(okey);
        if (node != null) {
            //如果存在，把这个节点移动到第一个
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
            //移走最后一个节点
            removeLastNode();
        }
        cacheLock.writeLock().unlock();
    }

    //移除节点node
    private void removeNode(CacheNode node) {
        cacheLock.writeLock().lock();
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        cacheLock.writeLock().unlock();
    }

    //移除最后一个节点
    private void removeLastNode() {
        cacheLock.writeLock().lock();
        listHead.prev.prev.next = listHead;
        listHead.prev = listHead.prev.prev;
        size--;
        cacheLock.writeLock().unlock();
    }

    //往头部添加一个节点
    private void addFirst(CacheNode node) {
        cacheLock.writeLock().lock();
        node.next = listHead.next;
        node.prev = listHead;
        listHead.next.prev = node;
        listHead.next = node;
        size++;
        cacheLock.writeLock().unlock();
    }

    public void dumpCache() {
        cacheLock.readLock().lock();
        CacheNode pseudoHead = listHead;
        int i = 0;
        System.out.println("size:" + size);
        while (pseudoHead.next != null && i <= size) {
            System.out.println(pseudoHead.key == null ? "头节点" : pseudoHead.key + " " + pseudoHead.val);
            pseudoHead = pseudoHead.next;
            i++;
        }
        cacheLock.readLock().unlock();
    }

    public static void main(String[] args) {
        final CacheContainer cacheContainer = new CacheContainer(10);
        for (int i = 0; i < 15; i++) {
            Integer integer = new Integer(i);
            Integer integer1 = new Integer(i + 100);
            cacheContainer.set(integer, integer1);
            System.out.println("放入 " + i + " 后的缓存-----");
            cacheContainer.dumpCache();
        }
        cacheContainer.dumpCache();
        for (int i = 0; i < 15; i++) {
            System.out.println("key: " + i + " value:" + cacheContainer.get(i));
            System.out.println("得到" + i + "后的缓存-------");
            cacheContainer.dumpCache();
        }
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    cacheContainer.set(new Integer((int) (Math.random() * 100)), new Integer((int) (Math.random() * 100)));
                }
            });
            service.submit(thread);
        }
        cacheContainer.dumpCache();
    }
}
