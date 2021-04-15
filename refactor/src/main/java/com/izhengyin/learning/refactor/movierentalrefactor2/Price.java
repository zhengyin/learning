package com.izhengyin.learning.refactor.movierentalrefactor2;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/15 4:21 下午
 */
public abstract class Price {
    /**
     * 获取租聘总价
     * @param daysRented
     * @return
     */
    public abstract double getCharge(int daysRented);

    /**
     * 积分
     * @param daysRented
     * @return
     */
    public int getPoint(int daysRented){
        return 1;
    }
}
