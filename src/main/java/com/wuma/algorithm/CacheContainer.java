package com.wuma.algorithm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ʵ��һ������������֧������ָ���ͳ������������ȶ���̭��ͬʱ�����ܱ�֤���̻߳����Ķ�д���ܣ�����ʹ���ⲿ������
 * ��дҪ��������case
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
    //����������
    private int capacity;
    //�������ڵĴ�С
    volatile int size;
    private ReadWriteLock cacheLock;

    public CacheContainer(int capacity) {
        this.capacity = capacity;
        listHead.prev = listHead;
        listHead.next = listHead;
        this.size = 0;
        //����һ����д������߲�����������
        cacheLock = new ReentrantReadWriteLock(false);
    }

    public int get(int key) {
        //���в���д�ڵ��ʱ�򣬶�������
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
            //������ڣ�������ڵ��ƶ�����һ��
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
            //�������һ���ڵ�
            removeLastNode();
        }
        cacheLock.writeLock().unlock();
    }

    //�Ƴ��ڵ�node
    private void removeNode(CacheNode node) {
        cacheLock.writeLock().lock();
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        cacheLock.writeLock().unlock();
    }

    //�Ƴ����һ���ڵ�
    private void removeLastNode() {
        cacheLock.writeLock().lock();
        listHead.prev.prev.next = listHead;
        listHead.prev = listHead.prev.prev;
        size--;
        cacheLock.writeLock().unlock();
    }

    //��ͷ�����һ���ڵ�
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
            System.out.println(pseudoHead.key == null ? "ͷ�ڵ�" : pseudoHead.key + " " + pseudoHead.val);
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
            System.out.println("���� " + i + " ��Ļ���-----");
            cacheContainer.dumpCache();
        }
        cacheContainer.dumpCache();
        for (int i = 0; i < 15; i++) {
            System.out.println("key: " + i + " value:" + cacheContainer.get(i));
            System.out.println("�õ�" + i + "��Ļ���-------");
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
