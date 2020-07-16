package com.concurrent.threadpool;

import java.util.concurrent.*;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/16 14:15
 * @Description:
 */
public class RejectPolicyTest {
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(1,
                2, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        try {
            for (int i = 0; i<100; i++) {
                System.out.println(i);
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
