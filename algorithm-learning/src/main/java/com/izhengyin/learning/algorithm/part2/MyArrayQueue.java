package com.izhengyin.learning.algorithm.part2;

public class MyArrayQueue<E>  implements MyQueue<E>{

    private Object[] elements;
    private int capacity;
    private int head = 0;
    private int tail = 0;

    public MyArrayQueue(int capacity){
        this.capacity = capacity;
        elements = new Object[this.capacity];
    }

    /**
     * 入队 O(n)
     * 1. tail == capacity ， 并且 head == 0 代表队列没有可用空间
     * 2. 当队列有可用空间时，进行数据搬移
     * @param e
     * @return
     */
    public boolean enqueue(E e) {
        if(tail == capacity){
            // 队列满了
            if(head == 0){
                return false;
            }
            for(int i=head;i<tail;i++){
                elements[i-head] = elements[i];
            }
            tail = tail - head;
            head = 0;
        }
        this.elements[tail] = e;
        tail ++;
        return true;
    }

    /**
     * 出队 O(1)
     * @return
     */
    @SuppressWarnings("unchecked")
    public E dequeue() {
        if(head == tail){
            return null;
        }
        E e = (E)elements[head];
        head ++;
        return e;
    }
}
