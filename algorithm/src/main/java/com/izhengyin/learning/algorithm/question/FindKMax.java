package com.izhengyin.learning.algorithm.question;

import com.alibaba.fastjson.JSON;

/**
 * O(n) 时间复杂度内求无序数组中的第 K 大元素。
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-11-26 10:04
 */
public class FindKMax {
    public static void main(String[] args) {
        int[] arr = new int[]{5,3,10,1,6,4,3,5,7,9};
        int k = 5;
        int l = 0;
        int r = arr.length - 1;
        while (true){
            int p = partition(arr,l,r);
            System.out.println(p+" -> "+ JSON.toJSONString(arr));
            if(p == arr.length - k){
                System.out.println("第 "+k+" 大的元素是 "+arr[p]+" , p = "+p);
                break;
            }else if(p < arr.length - k){
                l = p + 1;
            }else{
                r = p-1;
            }
        }
    }

    private static int partition(int[] arr , int lIndex , int rIndex){
        int l = lIndex;
        int r = rIndex;
        int base = arr[l];
        while (l < r){
            while (l < r && arr[r] >= base){
                r --;
            }
            arr[l] = arr[r];
            while (l < r && arr[l] <= base){
                l ++;
            }
            arr[r] = arr[l];
        }
        arr[l] = base;
        return l;
    }
}