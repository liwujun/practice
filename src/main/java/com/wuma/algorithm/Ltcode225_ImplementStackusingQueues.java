package com.wuma.algorithm;

import java.util.LinkedList;

/**
 * Created by wuma
 * on 2017/2/15 at 18:08
 * Implement the following operations of a stack using queues.
 * <p/>
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * empty() -- Return whether the stack is empty.
 * Notes:
 * You must use only standard operations of a queue --
 * which means only push to back, peek/pop from front, size, and is empty operations are valid.
 * Depending on your language, queue may not be supported natively.
 * You may simulate a queue by using a list or deque (double-ended queue),
 * as long as you use only standard operations of a queue.
 * You may assume that all operations are valid (for example,
 * no pop or top operations will be called on an empty stack).
 */
public class Ltcode225_ImplementStackusingQueues {
    /**
     * Initialize your data structure here.
     */

    public static class MyStack {
        LinkedList<Integer> queue1;
        LinkedList<Integer> queue2;
        int size;

        public MyStack() {
            queue1 = new LinkedList<Integer>();
            queue2 = new LinkedList<Integer>();
            size = 0;
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            queue1.offer(x);
            size++;
        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            int result = queue1.peek();
            int count = 0;
            if (size == 0) {
                return result;
            }
            while (!queue1.isEmpty() && (size > count + 1)) {
                queue2.offer(result = queue1.poll());
                count++;
            }
            result = queue1.poll();
            while (!queue2.isEmpty()) {
                queue1.offer(queue2.poll());
            }
            size--;
            return result;
        }

        /**
         * Get the top element.
         */
        public int top() {
            int result = queue1.peek();
            while (!queue1.isEmpty()) {
                queue2.offer(result = queue1.poll());
            }
            while (!queue2.isEmpty()) {
                queue1.offer(queue2.poll());
            }
            return result;
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {

            return queue1.isEmpty();
        }
    }

    public static void main(String args[]) {

        MyStack obj = new MyStack();
        obj.push(1);
        int param_2 = obj.pop();
        int param_3 = obj.top();
        boolean param_4 = obj.empty();

    }

}

