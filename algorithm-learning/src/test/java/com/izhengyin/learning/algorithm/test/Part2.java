package com.izhengyin.learning.algorithm.test;

import com.izhengyin.learning.algorithm.part2.MyQueue;
import com.izhengyin.learning.algorithm.part2.MyArrayQueue;
import com.izhengyin.learning.algorithm.part2.MyCircularQueue;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Part2 {

    @Test
    public void myArrayQueueTest(){
        MyQueue<String> queue = new MyArrayQueue<String>(5);
        queue.enqueue("a");
        Assert.assertEquals(queue.dequeue(),"a");
        queue.enqueue("b");
        Assert.assertEquals(queue.dequeue(),"b");
        queue.enqueue("c");
        Assert.assertEquals(queue.dequeue(),"c");
        queue.enqueue("d");
        queue.enqueue("e");
        queue.enqueue("f");
        queue.enqueue("g");
        queue.enqueue("h");
        Assert.assertFalse(queue.enqueue("s"));
        Assert.assertEquals(queue.dequeue(),"d");
        Assert.assertEquals(queue.dequeue(),"e");
        Assert.assertEquals(queue.dequeue(),"f");
        Assert.assertEquals(queue.dequeue(),"g");
        Assert.assertEquals(queue.dequeue(),"h");
        Assert.assertNull(queue.dequeue());
    }

    @Test
    public void myCircularQueueTest(){
        MyQueue<String> queue = new MyCircularQueue<String>(5);
        queue.enqueue("a");
        Assert.assertEquals(queue.dequeue(),"a");
        queue.enqueue("b");
        Assert.assertEquals(queue.dequeue(),"b");
        queue.enqueue("c");
        Assert.assertEquals(queue.dequeue(),"c");
        queue.enqueue("d");
        queue.enqueue("e");
        queue.enqueue("f");
        queue.enqueue("g");
        Assert.assertFalse(queue.enqueue("h"));
        Assert.assertEquals(queue.dequeue(),"d");
        Assert.assertEquals(queue.dequeue(),"e");
        Assert.assertEquals(queue.dequeue(),"f");
        Assert.assertEquals(queue.dequeue(),"g");
        Assert.assertNull(queue.dequeue(),"h");
    }
}
