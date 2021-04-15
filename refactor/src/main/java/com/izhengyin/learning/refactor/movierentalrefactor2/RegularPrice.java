package com.izhengyin.learning.refactor.movierentalrefactor2;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/15 4:24 下午
 */
public class RegularPrice extends Price{
    @Override
    public double getCharge(int daysRented) {
        double thisAmount = 2;
        if(daysRented > 2){
            thisAmount += (daysRented - 2) * 1.5;
        }
        return thisAmount;
    }
}
