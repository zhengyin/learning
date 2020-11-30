package com.izhengyin.learning.algorithm.question;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 现在你有 10 个接口访问日志文件，每个日志文件大小约 300MB，每个文件里的日志都是按照时间戳从小到大排序的。你希望将这 10 个较小的日志文件，合并为 1 个日志文件，合并之后的日志仍然按照时间戳从小到大排列。如果处理上述排序任务的机器内存只有 1GB，你有什么好的解决思路，能“快速”地将这 10 个日志文件合并吗？
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-11-25 16:01
 */
public class SortMerge {

    private static List<ArrayBlockingQueue<Integer>> source = new ArrayList<ArrayBlockingQueue<Integer>>(10);

    // mock10个IO流
    static {
        int[][] arr = new int[10][10];

        for (int i=0;i<10;i++){
            Random r = new Random();
            int size = r.nextInt(10) + 1;
            int[] v = new int[size];
            int base = r.nextInt(100) + 1;
            for (int n = base;n< size + base ;n++){
                v[n - base] = n;
            }
            arr[i] = v;
        }

        // 原始数据
        for (int i=0;i<arr.length;i++){
            int l = arr[i].length;
            ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(l);
            for (int n = 0;n<l;n++){
                queue.add(arr[i][n]);
            }
            source.add(queue);
        }

    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(source, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));


        int[] arr = new int[source.size()];
        for (int i=0;i<source.size();i++){
            arr[i] = readValue(i);
        }
        int emptyCounter = arr.length;

        while (true){

            //每次找最小值
            int min = Integer.MAX_VALUE;
            int point = -1;
            for (int i=0;i<arr.length;i++){
                if(arr[i] <= min){
                    min = arr[i];
                    point = i;
                }
            }

            //打印出最小值
            System.out.println(min);

            //从最小的 Queue(IO)除取出下一个值
            Integer newValue =  readValue(point);
            if(newValue != null){
                arr[point] = newValue;
            }else{
                //当前队列没有数据了
                arr[point] = Integer.MAX_VALUE;
                emptyCounter -- ;
            }
            if(emptyCounter <= 0){
                break;
            }
        }

    }
    private static Integer readValue(int index){
        return source.get(index).poll();
    }
}
