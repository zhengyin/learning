package com.izhengyin.learning.algorithm.part4;
import com.alibaba.fastjson.JSON;

/**
 * 计数排序
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-11-28 11:52
 */
public class CounterSort {
    public static void main(String[] args) {
        int[] arr = new int[]{1,6,7,7,8,2,3,2,4,5,3,0,2};
        //找出最大值，最小值
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i : arr){
            if(i < min){
                min = i;
            }
            if(i > max){
                max = i;
            }
        }
        int[] counters = new int[max - min + 1];
        //统计每个数出现次数
        for (int i : arr){
            int index = i - min;
            counters[index] ++;
        }
        System.out.println(JSON.toJSONString(counters));
        //累加
        for (int i = 0; i<counters.length; i++){
            if(i > 0){
                counters[i] = counters[i] + counters[i - 1];
            }
        }
        System.out.println(JSON.toJSONString(counters));
        int[] result = new int[arr.length];
        for (int i = arr.length - 1 ; i>=0; i--){
            int index = arr[i] - min;
            result[counters[index] - 1] = arr[i];
            counters[index] --;
        }
        System.out.println(JSON.toJSONString(result));

    }

}
