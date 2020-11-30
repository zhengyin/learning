package com.izhengyin.learning.algorithm.part3;

import com.alibaba.fastjson.JSON;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Create on 2020/11/22 12:21 下午
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = new int[]{7,1,4,2,3,5,0,10,2,8,6,7,9};
        System.out.println(JSON.toJSONString(arr));
        sort(arr);
     //   insertionSort(arr,arr.length);
    }


    public static void sort(int[] arr){
        if(arr.length <= 1){
            return;
        }
        for (int i=1;i<arr.length;i++){
            for (int j = i;j>0;j--){
                if(arr[j] < arr[j-1]){
                    int tmp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = tmp;
                }else{
                    break;
                }
            }
            System.out.println(JSON.toJSONString(arr));
        }
    }


    // 插入排序，a表示数组，n表示数组大小
    public static void insertionSort(int[] a, int n) {
        if (n <= 1) {
            return;
        }

        for (int i = 1; i < n; ++i) {
            int value = a[i];
            int j = i - 1;
            // 查找插入的位置
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j+1] = a[j];  // 数据移动
                } else {
                    break;
                }
            }
            System.out.println(j+1);
            a[j+1] = value; // 插入数据
            System.out.println(JSON.toJSONString(a));
        }
    }

}
