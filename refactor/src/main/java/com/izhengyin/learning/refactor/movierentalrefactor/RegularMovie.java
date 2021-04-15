package com.izhengyin.learning.refactor.movierentalrefactor;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/13 12:26 下午
 */
public class RegularMovie extends Movie {
    public RegularMovie(String title) {
        super(title);
    }
    @Override
    public double getCharge(int daysRented) {
        double thisAmount = 2;
        if(daysRented > 2){
            thisAmount += (daysRented - 2) * 1.5;
        }
        return thisAmount;
    }

    @Override
    public int getPoint(int daysRented) {
        return 1;
    }
}
