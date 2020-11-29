package com.izhengyin.learning.algorithm.test;

import com.izhengyin.learning.algorithm.part1.MyArrayList;
import com.izhengyin.learning.algorithm.part1.MyLinkedList;
import com.izhengyin.learning.algorithm.part1.MyList;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2018/11/10 2:00 PM
 */

public class Part1 {
    @BeforeClass
    public void setUp() {

    }

    @Test
    public void myArrayListTest() {
        MyList<String> myList = new MyArrayList<String>(10);
        myList.add("a");
        myList.add("b");
        myList.add("c");
        Assert.assertEquals(myList.size(),3);
        Assert.assertEquals(myList.get(2),"c");
        Assert.assertEquals(myList.remove(1),"b");
        Assert.assertEquals(myList.get(1),"c");
        Assert.assertTrue(myList.add("1"));
        Assert.assertTrue(myList.add("2"));
        Assert.assertFalse(myList.add(8,"3"));
        Assert.assertEquals(myList.size(),4);
        Assert.assertEquals(myList.get(3),"2");
    }

    @Test
    public void MyLinkedListTest() {
        MyList<String> myList = new MyLinkedList<String>();
        myList.add("a");
        myList.add("b");
        myList.add("c");
        Assert.assertEquals(myList.size(),3);
        Assert.assertEquals(myList.get(2),"c");
        Assert.assertEquals(myList.remove(1),"b");
        Assert.assertEquals(myList.get(1),"c");
        Assert.assertTrue(myList.add("1"));
        Assert.assertTrue(myList.add("2"));
        Assert.assertFalse(myList.add(8,"3"));
        Assert.assertEquals(myList.size(),4);
        Assert.assertEquals(myList.get(3),"2");
    }

}
