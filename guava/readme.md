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

### bimap 

> bimap 可以方便的帮助我们实现 key => value 到  value => key 的映射。基于这个特点 bimap 要求 value 唯一

``` 
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
```

* BiMap的实现类

``` 
　　Key-Value Map Impl     Value-Key Map Impl     Corresponding BiMap
　　HashMap                HashMap                HashBiMap
　　ImmutableMap           ImmutableMap           ImmutableBiMap
　　EnumMap                EnumMap                EnumBiMap
　　EnumMap                HashMap                EnumHashBiMap
```
 
### Table 

> 可以想象为一个Excel通过 Table<R, C, V> <行、列、值> 组织元素。

``` 
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
```

### ClassToInstanceMap

```
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
```

### RangeSet

> 根据API生产不同范围的数列

``` 
    RangeSet<Integer> rangeSet = TreeRangeSet.create();
    rangeSet.add(Range.closed(1, 10)); // {[1, 10]}
    rangeSet.add(Range.closedOpen(11, 15)); // disconnected range: {[1, 10], [11, 15)}
    rangeSet.add(Range.closedOpen(15, 20)); // connected range; {[1, 10], [11, 20)}
    rangeSet.add(Range.openClosed(0, 0)); // empty range; {[1, 10], [11, 20)}
    rangeSet.remove(Range.open(5, 10)); // splits [1, 10]; {[1, 5], [10, 10], [11, 20)}
    rangeSet.asRanges().forEach(System.out::println);
```

### RangeMap

> 范围数列到值的映射

``` 
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
```

## 集合工具

> Guava 集合工具提供了比 java.util.Collections 更多的集合操作的工具方法。

[Collection Utilities](https://github.com/google/guava/wiki/CollectionUtilitiesExplained)

``` 

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

```