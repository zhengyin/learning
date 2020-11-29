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

    @Test
    public void bimap(){
        BiMap<Integer,String> biMap = HashBiMap.create();
        IntStream.range(0,10)
                .forEach(i -> {
                    biMap.put(i,"i-"+i);
                });
        System.out.println("biMap "+biMap);
        //inverse方法会返回一个反转的BiMap，但是注意这个反转的map不是新的map对象，它实现了一种视图关联，这样你对于反转后的map的所有操作都会影响原先的map对象。
        BiMap<String,Integer> inverseBiMap = biMap.inverse();
        System.out.println("inverse biMap "+inverseBiMap);
        Assert.assertEquals(inverseBiMap.get("i-1"),new Integer(1));
        //原值修改，反转对象也会跟着被修改
        biMap.put(100,"i-100");
        System.out.println("inverse biMap "+inverseBiMap);
        Assert.assertEquals(inverseBiMap.get("i-100"),new Integer(100));
    }

    @Test
    public void table(){
        // Table<R, C, V> <行、列、值>
        Table<String, Integer, String> aTable = HashBasedTable.create();
        StringBuilder builder = new StringBuilder();
        for (char r = 'A'; r <= 'C'; ++r) {
            for (Integer c = 1; c <= 3; ++c) {
                aTable.put(Character.toString(r), c, String.format("%s%d", r, c));
            }
        }
        //获取第二列
        Map<String,String> column2 = aTable.column(2);
        Assert.assertEquals(column2.get("A"),"A2");
        Assert.assertEquals(column2.get("B"),"B2");
        Assert.assertEquals(column2.get("C"),"C2");
        //获取第B行
        Map<Integer,String> rowB = aTable.row("B");
        Assert.assertEquals(rowB.get(1),"B1");
        Assert.assertEquals(rowB.get(2),"B2");
        Assert.assertEquals(rowB.get(3),"B3");
        //检查是否包含D行1列
        Assert.assertFalse(aTable.contains("D", 1));
        //检查是否包含第3列
        Assert.assertTrue(aTable.containsColumn(3));
        Assert.assertTrue(aTable.containsRow("C"));
        System.out.println(aTable.columnMap());
        System.out.println(aTable.rowMap());
        Assert.assertEquals(aTable.remove("B", 3),"B3");
    }

    @Test
    public void classToInstanceMap(){
        ClassToInstanceMap<Number> numberDefaults = MutableClassToInstanceMap.create();
        numberDefaults.putInstance(Integer.class, 0);
        numberDefaults.putInstance(Double.class, 1.1d);
        numberDefaults.putInstance(Float.class, 2.2f);
        System.out.println(numberDefaults.keySet());
        System.out.println(numberDefaults.values());
        Assert.assertEquals(numberDefaults.get(Integer.class),new Integer(0));
        Assert.assertEquals(numberDefaults.get(Double.class),new Double(1.1));
        Assert.assertEquals(numberDefaults.get(Float.class),new Float(2.2));
    }

    @Test
    public void rangeSet(){
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // {[1, 10]}
        rangeSet.add(Range.closedOpen(11, 15)); // disconnected range: {[1, 10], [11, 15)}
        rangeSet.add(Range.closedOpen(15, 20)); // connected range; {[1, 10], [11, 20)}
        rangeSet.add(Range.openClosed(0, 0)); // empty range; {[1, 10], [11, 20)}
        rangeSet.remove(Range.open(5, 10)); // splits [1, 10]; {[1, 5], [10, 10], [11, 20)}
        rangeSet.asRanges().forEach(System.out::println);

    }

    @Test
    public void rangeMap(){
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "foo"); // {[1, 10] => "foo"}
        rangeMap.put(Range.open(3, 6), "bar"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo"}
        rangeMap.put(Range.open(10, 20), "foo"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo", (10, 20) => "foo"}
        rangeMap.remove(Range.closed(5, 11)); // {[1, 3] => "foo", (3, 5) => "bar", (11, 20) => "foo"}
        int a = 2;
        int b = 5;
        int c = 10;
        rangeMap.asMapOfRanges()
                .forEach((range,value) -> {
                    System.out.println(range +" -> "+value);
                    if(range.contains(a)){
                        Assert.assertEquals(value,"foo");
                    }
                    if(range.contains(b)){
                        Assert.assertEquals(value,"bar");
                    }
                    if(range.contains(c)){
                        Assert.assertEquals(value,"foo");
                    }
                });
    }


    @Data
    @ToString
    private static class Student{
        private String name;
    }
}
