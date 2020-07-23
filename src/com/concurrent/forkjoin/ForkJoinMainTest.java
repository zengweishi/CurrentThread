package com.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/17 16:26
 * @Description:
 */
public class ForkJoinMainTest {
    public static void main(String[] args) {//317811,514229
        int i[] = {0,1,1,2,3,5,8,13,21,34,55,89,144,233,377,610,987,1597,2584,4181,6765,10946,17711,28657,46368,75025,121393,196418};
        int s = 0;
        for (int j = 0;j < 28;j++) {
            s += i[j];
        }
        System.out.println(s);
        //创建分治任务线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        //创建分治任务
        Fibonacci fibonacci = new Fibonacci(5);
        //启动分治任务
        Integer result = forkJoinPool.invoke(fibonacci);
        //输出结果
        System.out.println("re:"+result);
    }
}
