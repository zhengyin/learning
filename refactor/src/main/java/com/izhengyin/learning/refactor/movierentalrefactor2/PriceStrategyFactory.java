package com.izhengyin.learning.refactor.movierentalrefactor2;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/15 5:00 下午
 */
public class PriceStrategyFactory {
    private static final Map<Integer,Price> REPOSITORY = new ConcurrentHashMap<>();
    static {
        REPOSITORY.put(PriceCode.CHILDRENS,new ChildrenPrice());
        REPOSITORY.put(PriceCode.REGULAR,new RegularPrice());
        REPOSITORY.put(PriceCode.NEW_RELEASE,new NewReleasePrice());
    //    REPOSITORY.put(PriceCode.DOCUMENTARY,new DocumentaryPrice());
    }

    /**
     * 获取 price 对象
     * @param priceCode
     * @return
     */
    public static Price get(int priceCode){
        Price price = REPOSITORY.get(priceCode);
        Objects.requireNonNull(price,"获取 Price 失败");
        return price;
    }
}
