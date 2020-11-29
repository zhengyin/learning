# Guava

## 集合

### 不可变集合

* 为什么要使用Guava的不可变集合？
1. 安全的使用不信任的包
2. 线程安全，因为不可变所以不用担心线程竞争导致的多线程问题
3. 可以像使用常量一样使用集合元素

* GDK提供的 Collections.unmodifiableXXX 有什么问题？
1. 笨拙而冗余的 ？
2. 不安全 ？
3. 低效的，GDK Collections.unmodifiableXXX 里面还是使用可变集合的数据结构和API

* 创建方式
```
    //copyOf
    ImmutableSet.copyOf(list);
    //of
    ImmutableSet.of("a", "b", "c", "a", "d", "b");
    //builder
    ImmutableSet<String> strings = ImmutableSet.<String>builder()
            .addAll(list)
            .build();
```

### Multiset

> multiset 不是一个set ， 也不是一个Map<E,Integer>,尽管Multiset提供一部分类似的功能实现。

* Apis

```
    @Test
    public void multiset(){
        Multiset<String> multiset = HashMultiset.create(list);
        Assert.assertEquals(multiset.size(),9);
        Assert.assertEquals(multiset.count("a"),2);
        multiset.removeAll(Arrays.asList("a","b","c"));
        Assert.assertEquals(multiset.size(),6);
    }
```

``` 
　　　　add(E element) :向其中添加单个元素
　　　　add(E element,int occurrences) : 向其中添加指定个数的元素
　　　　count(Object element) : 返回给定参数元素的个数
　　　　remove(E element) : 移除一个元素，其count值 会响应减少
　　　　remove(E element,int occurrences): 移除相应个数的元素
　　　　elementSet() : 将不同的元素放入一个Set中
　　　　entrySet(): 类似与Map.entrySet 返回Set<Multiset.Entry>。包含的Entry支持使用getElement()和getCount()
　　　　setCount(E element ,int count): 设定某一个元素的重复次数
　　　　setCount(E element,int oldCount,int newCount): 将符合原有重复个数的元素修改为新的重复次数
　　　　retainAll(Collection c) : 保留出现在给定集合参数的所有的元素
　　　　removeAll(Collectionc) : 去除出现给给定集合参数的所有的元素
```

* Guava提供了Multiset的多种实现，这些实现基本对应了JDK中Map的实现：
``` 
　　JDK                   Multiset  实现             是否支持空元素
　　HashMap               HashMultiset               Yes
　　TreeMap               TreeMultiset               Yes (if the comparator does)
　　LinkedHashMap         LinkedHashMultiset         Yes
　　ConcurrentHashMap     ConcurrentHashMultiset     No
　　ImmutableMap          ImmutableMultiset          No
```

### Multimap 

* Multimap 对于组合类似 Map<String, List<String>> map = new HashMap<String, List<String>>(); 这样的数据结构非常便捷

``` 
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
```

* Multimap提供了丰富的实现，所以你可以用它来替代程序里的Map<K, Collection<V>>，具体的实现如下：
``` 
　　Implementation            Keys 的行为类似       　　　Values的行为类似
　　ArrayListMultimap         HashMap                   ArrayList
　　HashMultimap              HashMap                   HashSet
　　LinkedListMultimap        LinkedHashMap*            LinkedList*
　　LinkedHashMultimap        LinkedHashMap             LinkedHashSet
　　TreeMultimap              TreeMap                   TreeSet
　　ImmutableListMultimap     ImmutableMap              ImmutableList
　　ImmutableSetMultimap      ImmutableMap              ImmutableSet
```