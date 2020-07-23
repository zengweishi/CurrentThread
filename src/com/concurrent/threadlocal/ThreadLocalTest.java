package com.concurrent.threadlocal;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/20 13:29
 * @Description:
 */
public class ThreadLocalTest {
    static final AtomicLong
            nextId=new AtomicLong(0);
    static final  ThreadLocal<Object> threadLocal = ThreadLocal.withInitial(()->nextId.getAndIncrement());
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread1:"+threadLocal.get());
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread1:"+threadLocal.get());
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread2:"+threadLocal.get());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread2:"+threadLocal.get());
            }
        });
        thread1.start();
        thread2.start();
    }
}
