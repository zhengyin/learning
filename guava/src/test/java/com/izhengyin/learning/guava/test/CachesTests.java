package com.izhengyin.learning.guava.test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ConcurrentModificationException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-11-30 17:33
 */
public class CachesTests {

    @Test
    public void cache() throws ExecutionException{
        Cache<Integer,String> caches = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .removalListener((notification) -> System.out.println(notification.getKey() +" => "+notification.getValue()+" , "+notification.getCause()))
                .build();
        Assert.assertNull(caches.get(1, new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        }));

    }

    @Test
    public void loadingCache() throws ExecutionException , InterruptedException {
        LoadingCache<Integer, String> caches = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build(
                    new CacheLoader<Integer, String>() {
                        @Override
                        public String load(Integer key)  {
                            return "#"+key;
                        }
                    }
                );
        Assert.assertEquals(caches.get(1),"#1");
        Assert.assertEquals(caches.get(2),"#2");
        Assert.assertEquals(caches.get(3),"#3");
        TimeUnit.SECONDS.sleep(2);
    }
}
