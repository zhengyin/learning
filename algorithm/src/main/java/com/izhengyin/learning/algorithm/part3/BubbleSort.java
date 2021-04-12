package com.izhengyin.learning.algorithm.part3;

import com.alibaba.fastjson.JSON;

/**
 * 冒泡排序
 * @author zhengyin zhengyinit@outlook.com
 * Create on 2020/11/22 12:22 下午
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = new int[]{1,4,2,3,5,0};
        System.out.println(JSON.toJSONString(arr));
        sort(arr);
    }
    public static void sort(int[] arr){
        if(arr.length <= 1){
            return;
        }
        for (int i=0;i<arr.length;i++){
            boolean dataChange = false;
            for (int j=0;j<arr.length-1-i;j++){
                if(arr[j] > arr[j+1]){
                    int v = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = v;
                    dataChange = true;
                }
            }
            if(!dataChange){
                break;
            }
            System.out.println(JSON.toJSONString(arr));
        }

    }

}
