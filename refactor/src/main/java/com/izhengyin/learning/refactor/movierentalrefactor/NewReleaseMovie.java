package com.izhengyin.learning.refactor.movierentalrefactor;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/13 12:26 下午
 */
public class NewReleaseMovie extends Movie {
    public NewReleaseMovie(String title) {
        super(title);
    }
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
