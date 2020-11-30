package com.izhengyin.learning.algorithm.part4;

import com.alibaba.fastjson.JSON;

/**
 * 基数排序
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-11-28 14:14
 */
public class RadixSort {
    public static void main(String[] args) {
        int[] arr = new int[]{187,123,134,453,133};

        radixSort(arr);
        System.out.println(JSON.toJSONString(arr));
    }

    /**
     * 高位优先法
     *
     * @param arr 待排序列，必须为自然数
     */
    private static void radixSort(int[] arr) {
        //待排序列最大值
        int max = arr[0];
        int exp;//指数

        //计算最大值
        for (int anArr : arr) {
            if (anArr > max) {
                max = anArr;
            }
        }

        //从个位开始，对数组进行排序
        for (exp = 1; max / exp > 0; exp *= 10) {
            //存储待排元素的临时数组
            int[] temp = new int[arr.length];
            //分桶个数
            int[] buckets = new int[10];

            //将数据出现的次数存储在buckets中
            for (int value : arr) {
                //(value / exp) % 10 :value的最底位(个位)
                buckets[(value / exp) % 10]++;
            }

            //更改buckets[i]，
            for (int i = 1; i < 10; i++) {
                buckets[i] += buckets[i - 1];
            }

            //将数据存储到临时数组temp中
            for (int i = arr.length - 1; i >= 0; i--) {
                temp[buckets[(arr[i] / exp) % 10] - 1] = arr[i];
                buckets[(arr[i] / exp) % 10]--;
            }

            //将有序元素temp赋给arr
            System.arraycopy(temp, 0, arr, 0, arr.length);
        }

    }

    private static void main(int[] arr){
        for (int n=0;n<4;n++){
            System.out.println(n+" --- ");
            int[] tmp = new int[10];
            for (int i = 0; i< arr.length;i ++){
                int baseNum = arr[i] %  (int) Math.pow(10,n + 1);
                if(n > 0){
                    baseNum = baseNum / (int) Math.pow(10 , n);
                }
                tmp[baseNum] = arr[i];
            }
            System.out.println(JSON.toJSONString(tmp));
            int index = 0;
            for (int v : tmp){
                if(v > 0){
                    arr[index] = v;
                    index ++;
                }
            }
            System.out.println(JSON.toJSONString(arr));
        }
    }

}