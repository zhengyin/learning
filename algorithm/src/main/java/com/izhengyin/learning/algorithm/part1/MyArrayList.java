package com.izhengyin.learning.algorithm.part1;
/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2018/11/10 11:26 AM
 */
public class MyArrayList<E>  implements MyList<E>{

    private final static int DEFAULT_CAPACITY = 10;

    private Object[] elements;

    private int size = 0;

    public MyArrayList(){
        this(DEFAULT_CAPACITY);
    }

    public MyArrayList(int capacity){
        elements = new Object[capacity];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        if(size <= 0){
            return;
        }
        for(int i = size; i>0; i--){
            elements[i] = null;
        }
        size = 0;
    }

    public int size(){
        return size;
    }

    @SuppressWarnings("unchecked")
    public E get(int i){
       if(size == 0){
            throw new IndexOutOfBoundsException();
       }
       if (i < 0 || i >= size){
           throw new IllegalArgumentException();
       }
       return (E)elements[i];
    }

    public boolean add(E e){
        if(e == null){
            throw new NullPointerException();
        }
        return add(size,e);
    }

    public boolean add(int index , E e){
        if(e == null){
            throw new NullPointerException();
        }
        if(size >= elements.length){
            throw new IndexOutOfBoundsException();
        }
        if(index < 0 || index > size){
            return false;
        }
        if(index < size){
            for(int i = size;i>index;i--){
                elements[i] = elements[i-1];
            }
        }
        elements[index] = e;
        size ++;
        return true;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index){
        if(index<0 || index >= size){
            throw new IllegalArgumentException();
        }
        E oldElem = (E)elements[index];
        for (int i = index;i<size;i++){
            elements[i] = elements[i+1];
        }
        size --;
        elements[size] = null;
        return oldElem;
    }

    public int indexOf(E e){
        if(size <= 0){
            return -1;
        }
        for(int i=0;i<size;i++){
            if(elements[i].equals(e)){
                return i+1;
            }
        }
        return -1;
    }
}
