package com.izhengyin.learning.algorithm.part1;

/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2018/11/10 3:07 PM
 */
public class MyLinkedList<E>  implements MyList<E> {

    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    public MyLinkedList(){

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Node<E> node = first;
        while (node != null){
            Node<E> nextNode = node.next;
            node.item = null;
            node.next = null;
            node.prev = null;
            node = nextNode;
        }

        first = null;
        last = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public E getFirst() {
        if(first == null){
            return null;
        }
        return first.item;
    }

    public E getLast() {
        if(last == null){
            return null;
        }
        return last.item;
    }

    /**
     * @param i
     * @return
     */
    public E get(int i) {
        if(size == 0){
            throw new IndexOutOfBoundsException();
        }
        if (i < 0 || i >= size){
            throw new IllegalArgumentException();
        }
        return findNode(i).item;
    }

    /**
     * @param e
     * @return
     */
    public boolean add(E e) {
        if(e == null){
            throw new NullPointerException();
        }
        return add(size,e);
    }

    /**
     * 添加一个节点到 index 的位置
     * 1. size 为0 初始化链表
     * 2. 原本index的位置的节点，作为新节点的下一个节点
     * 3. 原本节点的上一个节点作为新节点的上一个节点
     * 4. 原本节点的上一个节点的下一个节点指向新节点
     * 5. index 为0 头结点指向新节点
     * 6. index 为 size 尾结点指向新节点
     * @param index
     * @param e
     * @return
     */
    public boolean add(int index, E e) {
        if(e == null){
            throw new NullPointerException();
        }
        if(index < 0 || index > size){
            return false;
        }
        //往末尾追加
        if(index == size){
            return addLast(e);
        }


        Node<E> node = findNode(index);
        Node<E> newNode = new Node<E>(node,e,node.prev);
        node.next = newNode;
        node.prev.next = newNode;
        //头结点
        if(index == 0){
            first = newNode;
        }

        size ++;
        return true;
    }

    /**
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        Node<E> node = findNode(index);
        node.prev.next = node.next;
        node.next = node.prev;
        size --;
        return node.item;
    }

    /**
     *
     * @param e
     * @return
     */
    public int indexOf(E e) {
        Node<E> node = first;
        for(int i = 0;i<size;i++){
            if(node.item.equals(e)){
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    private boolean addLast(E e){
        //初始化
        if(size == 0){
            return init(e);
        }
        Node<E> newNode = new Node<E>(first,e,last);
        last.next = newNode;
        first.prev = newNode;
        last = newNode;
        size ++;
        return true;
    }

    /**
     * 初始化空链表
     * @param e
     * @return
     */
    private boolean init(E e){
        Node<E> newNode = new Node<E>(null,e,null);
        newNode.prev = newNode;
        newNode.next = newNode;
        first = newNode;
        last = newNode;
        size ++;
        return true;
    }

    /**
     * 查找节点
     * 如果 index 为0直接返回头结点,否则从1开始循环，直到返回 index 所在的节点
     * @param index
     * @return
     */
    private Node<E> findNode(int index){
        Node<E> node = first;
        if (index == 0) {
            return node;
        }
        for(int i=1;i<=index;i++){
            node = node.next;
        }
        return node;
    }

    private class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
