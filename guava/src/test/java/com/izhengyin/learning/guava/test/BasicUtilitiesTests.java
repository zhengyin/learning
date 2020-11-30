package com.izhengyin.learning.guava.test;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import org.testng.annotations.Test;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-11-30 17:17
 */
public class BasicUtilitiesTests {

    @Test
    public void test(){
        int i = 0;
        Preconditions.checkArgument(i > 1 , "i 必须大于 1" , i);
    }

    @Test
    public void test2(){
        int i = 0;
        Verify.verify(i == 1,
                "Unexpected i: %s", i);
    }
}
