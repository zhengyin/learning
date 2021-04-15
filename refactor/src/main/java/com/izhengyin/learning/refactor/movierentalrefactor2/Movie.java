package com.izhengyin.learning.refactor.movierentalrefactor2;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/15 4:21 下午
 */
public class Movie {
    private final String title;
    private Price price;
    public Movie(String title,int priceCode){
        this.title = title;
        setPriceCode(priceCode);
    }

    /**
     * 设置价格计算规则编码
     * @param priceCode
     */
    public void setPriceCode(int priceCode){
        this.price = PriceStrategyFactory.get(priceCode);
    }

    public String getTitle() {
        return title;
    }

    public double getCharge(int daysRented){
        return this.price.getCharge(daysRented);
    }

    public int getPoint(int daysRented){
        return this.price.getPoint(daysRented);
    }

}
