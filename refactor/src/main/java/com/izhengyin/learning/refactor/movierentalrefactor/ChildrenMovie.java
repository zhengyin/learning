package com.izhengyin.learning.refactor.movierentalrefactor;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/13 12:25 下午
 */
public class ChildrenMovie extends Movie {
    public ChildrenMovie(String title) {
        super(title);
    }

    @Override
    public double getCharge(int daysRented) {
        double thisAmount = 1.5;
        if(daysRented > 3){
            thisAmount += (daysRented - 3) * 1.5;
        }
        return thisAmount;
    }

    @Override
    public int getPoint(int daysRented) {
        return 1;
    }
}
