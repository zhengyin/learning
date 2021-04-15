package com.izhengyin.learning.refactor.movierentalrefactor;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/13 12:24 下午
 */
public abstract class Movie {
    private String title;
    public Movie(String title){
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    /**
     * 获取租聘总价
     * @param daysRented
     * @return
     */
    public abstract double getCharge(int daysRented);

    public abstract int getPoint(int daysRented);
}
