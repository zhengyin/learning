package com.izhengyin.leanning.refactor.test;

import com.izhengyin.learning.refactor.movierental.Main;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/12 4:30 下午
 */
public class MovieRentalTest {
    @Test
    public void test(){
        Assert.assertEquals(Main.movierental(), com.izhengyin.learning.refactor.movierentalrefactor.Main.movierental());
    }
}
