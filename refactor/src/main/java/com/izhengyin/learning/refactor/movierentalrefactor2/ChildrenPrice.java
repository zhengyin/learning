package com.izhengyin.learning.refactor.movierentalrefactor2;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/15 4:24 下午
 */
public class ChildrenPrice extends Price{

    @Override
    public double getCharge(int daysRented) {
        double thisAmount = 1.5;
        if(daysRented > 3){
            thisAmount += (daysRented - 3) * 1.5;
        }
        return thisAmount;
    }
}
