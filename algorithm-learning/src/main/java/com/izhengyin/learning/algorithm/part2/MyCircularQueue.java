package com.izhengyin.learning.algorithm.part2;

public class MyCircularQueue<E> implements MyQueue<E> {

    private Object[] elements;
    private int capacity;
    private int head = 0;
    private int tail = 0;

    public MyCircularQueue(int capacity){
        this.capacity = capacity;
        elements = new Object[capacity];
    }

    /**
     * 入队 O(1)
     * 1. (tail+1) % capacity == head 队满，比如 capacity = 5 , tail = 4 , head = 0
     * 2. tail 元素添加后 tail 往后移动一位，当等于capacity 重新归 0
     * @param e
     * @return
     */
    public boolean enqueue(E e) {
        if((tail+1) % capacity == head){
            return false;
        }
        this.elements[tail] = e;
        tail = (tail+1) % capacity;
        return true;
    }

    /**
     * 出队 O(1)
     * 1. head == tail 空队列
     * 2. 去除元素后 head 往后移动一位，当等于capacity 重新归 0
     * @return E
     */
    @SuppressWarnings("unchecked")
    public E dequeue() {
        if(head == tail){
            return null;
        }
        E e = (E)elements[head];
        head = (head+1) % capacity;
        return e;
    }
}
