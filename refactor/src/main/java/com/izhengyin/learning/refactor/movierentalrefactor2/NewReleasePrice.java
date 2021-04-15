package com.izhengyin.learning.refactor.movierentalrefactor2;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/15 4:24 下午
 */
public class NewReleasePrice extends Price{
    @Override
    public double getCharge(int daysRented) {
        return daysRented;
    }
    @Override
    public int getPoint(int daysRented) {
        if(daysRented > 1){
            return 2;
        }
        return 1;
    }
}
