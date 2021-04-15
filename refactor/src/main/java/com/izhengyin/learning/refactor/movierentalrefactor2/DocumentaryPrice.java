package com.izhengyin.learning.refactor.movierentalrefactor2;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/15 5:16 下午
 */
public class DocumentaryPrice extends Price{

    @Override
    public double getCharge(int daysRented) {
        return daysRented * 2;
    }

}
