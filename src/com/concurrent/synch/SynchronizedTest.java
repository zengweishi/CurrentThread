package com.concurrent.synch;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/21 13:45
 * @Description:
 */
public class SynchronizedTest {
    //private long count = 0; 加上volatile解决可见性
    private volatile long count = 0;
    private void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            synchronized(this) {
                count++;
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        SynchronizedTest test = new SynchronizedTest();
        // 创建两个线程，执行 add() 操作
        Thread th1 = new Thread(()->{
            test.add10K();
        });
        Thread th2 = new Thread(()->{
            test.add10K();
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        th1.join();
        th2.join();
        // 介于1w-2w,即使加了volatile也达不到2w
        System.out.println(test.count);
    }
}
