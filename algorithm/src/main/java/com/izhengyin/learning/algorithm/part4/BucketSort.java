package com.izhengyin.learning.algorithm.part4;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 桶排序
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-11-27 11:54
 */
public class BucketSort {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,-4,98,2,45,24,-8,-3,-2,-1,1,5,6,3,7,8,15,0};
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
        //分桶范围 0~5,6~10
        int bucketNum = 5;
        int diff = max - min;
        int range = diff / bucketNum + (diff % bucketNum == 0 ? 0 : 1);

        System.out.println("diff -> "+diff);
        System.out.println("bucketNum -> "+bucketNum);
        System.out.println("range -> "+range);
        List<List<Integer>> bucket = new ArrayList<List<Integer>>();
        for (int n = 1; n<=bucketNum; n ++){
            bucket.add(new ArrayList<Integer>());
        }
        //数据分桶
        for (int i : arr){
            for (int n = 1; n<=bucketNum; n ++){
                if(i <= min + range * n){
                    bucket.get(n-1).add(i);
                    break;
                }
            }
        }
        System.out.println("bucket -> "+ JSON.toJSONString(bucket));
        //每个桶排序后合并
        System.out.println("result -> "+bucket.stream()
                .flatMap(list -> {
                    Collections.sort(list);
                    return list.stream();
                })
                .collect(Collectors.toList()));
    }
}
