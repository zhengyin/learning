package com.izhengyin.learning.reactor.test;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.testng.annotations.Test;
import reactor.core.publisher.Flux;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-30 12:12
 */
public class MyTest {
    @Test
    public void test(){
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }
            @Override
            public void onNext(String s) {
                System.out.println("onNext "+s);
            }
            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError ");
                throwable.printStackTrace();
            }
            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        Flux.just("a","b","c")
                .subscribe(mySubscriber);
    }
}
