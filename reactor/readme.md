# Reactor 

## 分析基本的Flux#subscribe执行过程

> 示例代码

``` 
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
                System.out.println("onNext");
            }
            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError");
            }
            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        Flux.just("a","b","c")
                .subscribe(mySubscriber);
    }
```

1. Flux#just 方法通过 FluxArray 完成的组装，先不管实现的细节，这里就是一个创建了一个 Publisher

```
public abstract class Flux<T> implements Publisher<T> {

    @SafeVarargs
    public static <T> Flux<T> just(T... data) {
        return fromArray(data);
    }

    public static <T> Flux<T> fromArray(T[] array) {
        if (array.length == 0) {
            return empty();
        } else {
            //通过 FluxArray 完成组装
            return array.length == 1 ? just(array[0]) : onAssembly((Flux)(new FluxArray(array)));
        }
    }
}

```

2. onLastAssembly 组装Flux我们没有定义全局Hooks，返回的就是 FluxArray

```
 
public abstract class Flux<T> implements Publisher<T> {


    public final void subscribe(Subscriber<? super T> actual) {
        onLastAssembly(this).subscribe(Operators.toCoreSubscriber(actual));
    }
    
    // .... 
    
    protected static <T> Flux<T> onLastAssembly(Flux<T> source) {
        Function<Publisher, Publisher> hook = Hooks.onLastOperatorHook;
        return hook == null ? source : (Flux)Objects.requireNonNull(hook.apply(source), "LastOperator hook returned null");
    }
}


```

3. 通过Operators.toCoreSubscriber将自定义的Subscriber包装为 CoreSubscriber，实现类是StrictSubscriber , 这里就是完成了一个Subscriber

``` 
public abstract class Operators {
    public static <T> CoreSubscriber<? super T> toCoreSubscriber(Subscriber<? super T> actual) {
        Objects.requireNonNull(actual, "actual");
        Object _actual;
        if (actual instanceof CoreSubscriber) {
            _actual = (CoreSubscriber)actual;
        } else {
            //执行到这里
            _actual = new StrictSubscriber(actual);
        }
        return (CoreSubscriber)_actual;
    }
}

final class StrictSubscriber<T> implements Scannable, CoreSubscriber<T>, Subscription {
    //构造函数注入实际的(mySubscriber) Subscriber
    StrictSubscriber(Subscriber<? super T> actual) {
        this.actual = actual;
    }
}

```

4. 调用 subscribe ，实际调用的是 FluxArray#subscribe 这里会创建一个 FluxArray.ArraySubscription , 这里就是一个 Subscription
``` 
final class FluxArray<T> extends Flux<T> implements Fuseable {
    public static <T> void subscribe(CoreSubscriber<? super T> s, T[] array) {
        if (array.length == 0) {
            Operators.complete(s);
        } else {
            if (s instanceof ConditionalSubscriber) {
                s.onSubscribe(new FluxArray.ArrayConditionalSubscription((ConditionalSubscriber)s, array));
            } else {
                //执行到这里
                s.onSubscribe(new FluxArray.ArraySubscription(s, array));
            }

        }
    }
}
```

5. 执行 onSubscribe , 实际执行的是StrictSubscriber#onSubscribe 这里会将StrictSubscriber传递给我们的 mySubscriber (StrictSubscriber也实现了Subscription接口) ，这时的StrictSubscriber可以看做 FluxArray.ArraySubscription 的代理类

``` 
final class StrictSubscriber<T> implements Scannable, CoreSubscriber<T>, Subscription {
    public void onSubscribe(Subscription s) {
        if (Operators.validate(this.s, s)) {
        
            this.actual.onSubscribe(this);
            if (Operators.setOnce(S, this, s)) {
                long r = REQUESTED.getAndSet(this, 0L);
                if (r != 0L) {
                    s.request(r);
                }
            }
        } else {
            this.onError(new IllegalStateException("§2.12 violated: onSubscribe must be called at most once"));
        }

    }
}
```

6. 当我们执行 subscription.request(Long.MAX_VALUE) 实际执行的过程是 StrictSubscriber#request -> FluxArray.ArraySubscription#request

``` 
    static final class ArraySubscription<T> implements InnerProducer<T>, SynchronousSubscription<T> {
        public void request(long n) {
            if (Operators.validate(n) && Operators.addCap(REQUESTED, this, n) == 0L) {
                if (n == 9223372036854775807L) {
                    //执行此方法
                    this.fastPath();
                } else {
                    this.slowPath(n);
                }
            }

        }
    }

```

7. fastPath 会执行StrictSubscriber定义的回调方法,StrictSubscriber会执行mySubscriber定义的回调方法
```

    static final class ArraySubscription<T> implements InnerProducer<T>, SynchronousSubscription<T> {
        void fastPath() {
            T[] a = this.array;
            int len = a.length;
            //这个是  StrictSubscriber 
            Subscriber<? super T> s = this.actual;

            for(int i = this.index; i != len; ++i) {
                if (this.cancelled) {
                    return;
                }

                T t = a[i];
                if (t == null) {
                    s.onError(new NullPointerException("The " + i + "th array element was null"));
                    return;
                }
                //在 StrictSubscriber 的 onNext 方法中会调研我们定义的 onNext 
                s.onNext(t);
            }

            if (!this.cancelled) {
                s.onComplete();
            }
        }
    }
```