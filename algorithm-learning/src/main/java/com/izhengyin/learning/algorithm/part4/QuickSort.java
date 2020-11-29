package com.izhengyin.learning.algorithm.part4;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.TimeUnit;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Create on 2020/11/22 7:38 下午
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[]{1,4,2,3,5,10,2,8,7,9,6};
        System.out.println(JSON.toJSONString(arr));
        quick_sort(arr);
        System.out.println(JSON.toJSONString(arr));
    }



    public static void quick_sort(int[] arr){
        quick_sort_c(arr,0,arr.length - 1);
    }


    public static void quick_sort_c(int[] arr , int s , int e){
        if(s >= e){
            return;
        }
        int p = getPartition(arr,s,e);
        quick_sort_c(arr,s,p );
        quick_sort_c(arr,p +1,e);
    }

    public static int getPartition(int[] arr , int s , int e){
        System.out.println(s+" -> "+e);
        int point = arr[s];
        int i = s;
        int j = s;
        for (int n=s;n<=e;n++){
            if(arr[i] < point){
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
            }
        }


        System.out.println(JSON.toJSONString(arr));

        return i;
    }

}
