package com.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/24 17:08
 * @Description:
 */
public class ForkJoinPoolTest {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(1000);
        IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
            System.out.println(Thread.currentThread().getName()+":"+i);
        });
        System.out.println("====================");
        //使用线程池并发处理逻辑
        //parallelStream().forEach(),并行遍历循环
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
            System.out.println(Thread.currentThread().getName()+":"+i);
        }));
        /*forkJoinPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < 10;i++) {
                    System.out.println(i);
                }
            }
        });*/
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);

    }
}
