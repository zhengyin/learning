package com.izhengyin.learning.guava.test;

import com.google.common.collect.*;
import lombok.Data;
import lombok.ToString;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.*;
import java.util.stream.IntStream;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Create on 2020/11/29 5:14 下午
 */

public class CollectionTests {
    private List<String> list = Arrays.asList("a","e","b","d","e","a","f","e","d");
    /**
     * 三种方式创建
     */
    @Test
    public void immutable(){
        //copyOf
        ImmutableSet.copyOf(list);
        //of
        ImmutableSet.of("a", "b", "c", "a", "d", "b");
        //builder
        ImmutableSet<String> strings = ImmutableSet.<String>builder()
                .addAll(list)
                .build();
    }

    @Test
    public void multiset(){
        Multiset<String> multiset = HashMultiset.create(list);
        Assert.assertEquals(multiset.size(),9);
        Assert.assertEquals(multiset.count("a"),2);
        multiset.removeAll(Arrays.asList("a","b","c"));
        Assert.assertEquals(multiset.size(),6);
    }

    @Test
    public void multimap() {
        Multimap<String,Student> smp = ArrayListMultimap.create();
        for (int i =0;i<list.size();i++) {
            Student student = new Student();
            student.setName(list.get(i));
            smp.put("key"+(i % 2),student);
        }
        System.out.println(smp.size());
        //keys视图返回的是个Multiset，这个Multiset是以不重复的键对应的个数作为视图。这个Multiset可以通过支持移除操作而不是添加操作来修改Multimap。
        System.out.println(smp.keys());
        //entries视图是把Multimap里所有的键值对以Collection<Map.Entry<K, V>>的形式展现。
        smp.entries().forEach(System.out::println);
        //keySet视图是把Multimap的键集合作为视图
        smp.keySet().forEach(System.out::println);
        //alues()视图能把Multimap里的所有值“平展”成一个Collection<V>。这个操作和Iterables.concat(multimap.asMap().values())很相似，只是它返回的是一个完整的Collection。
        smp.values().forEach(System.out::println);
        //asMap把自身Multimap<K, V>映射成Map<K, Collection<V>>视图。这个Map视图支持remove和修改操作，但是不支持put和putAll。严格地来讲，当你希望传入参数是不存在的key，而且你希望返回的是null而不是一个空的可修改的集合的时候就可以调用asMap().get(key)。（你可以强制转型asMap().get(key)的结果类型－对SetMultimap的结果转成Set，对ListMultimap的结果转成List型－但是直接把ListMultimap转成Map<K, List<V>>是不行的。）
        Assert.assertEquals(smp.asMap().get("key1").size(),4);
        Assert.assertEquals(smp.asMap().get("key0").size(),5);

    }

    @Data
    @ToString
    private static class Student{
        private String name;
    }
}
