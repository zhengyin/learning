package com.izhengyin.learning.guava.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-11-30 15:54
 */
public class CollectionUtilitiesTests {

    @Test
    public void newInstance(){
        Set<Integer> integers = Sets.newHashSet(1,2,3,4,5);
        List<Integer> integerList = Lists.newArrayListWithCapacity(5);
    }

    @Test
    public void iterables(){
        Iterable<Integer> integers = Iterables.concat(Ints.asList(1,2,3),Ints.asList(4,5,6));
        Assert.assertFalse(Iterables.isEmpty(integers));
        Assert.assertEquals(Iterables.size(integers),6);
        Integer[] arr = Iterables.toArray(integers,Integer.class);
        System.out.println(JSON.toJSONString(arr));
    }

    @Test
    public void list(){
        List<Integer> integerList = Lists.newArrayList(1,2,3,4,5);
        //拆分集合
        Assert.assertEquals(Lists.partition(integerList,3).get(1).get(0),new Integer(4));
        //反转
        Assert.assertEquals(Lists.reverse(integerList).get(0),new Integer(5));
    }

    @Test
    public void comparators(){
        int[] arr = new int[]{1,2,3,4,5};
        Assert.assertEquals(Ints.min(arr),1);
        Assert.assertEquals(Ints.max(arr),5);
        Ints.sortDescending(arr);
        System.out.println(JSON.toJSONString(arr));
    }

    @Test
    public void sets(){
        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        Sets.SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength); // contains "two", "three", "seven"
        Assert.assertEquals(intersection.size(),3);
        System.out.println(JSON.toJSONString(intersection));
    }


}