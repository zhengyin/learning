package com.izhengyin.learning.algorithm.part1;

/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2018/11/10 2:58 PM
 */
public interface MyList<E> {
    boolean isEmpty();
    void clear();
    int size();
    E get(int i);
    boolean add(E e);
    boolean add(int index, E e);
    E remove(int index);
    int indexOf(E e);
}
