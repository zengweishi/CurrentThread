package com.concurrent.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/16 16:17
 * @Description:
 * 洗水壶 -洗茶壶/洗茶杯/拿茶叶 -泡茶
 *        -烧水
 *
 * T1:洗茶壶（1分钟） 洗茶杯（2分钟） 拿茶叶（1分钟）泡茶
 * T2:洗水壶（5分钟）
 * T3:烧水（15分钟）
 *
 * T1要等待T3的开水
 * T3要等待T2的水壶
 */
public class FutureMainTest {
    public static void main(String[] args) {
        FutureTask<String> futureTask2 = new FutureTask<>(new T2Task());
        //T3的烧水需要等待T2洗好水壶才可以，T2未返回尝试get()的时候结果会阻塞
        FutureTask<String> futureTask3 = new FutureTask<>(new T3Task(futureTask2));
        //T1的泡茶需要等到T3执行烧完水才可以，futureTask3会返回开水，在执行T1里尝试futureTask3.get()拿到开水
        //由于Future的get方法是阻塞式的，所以只有futureTask3执行完毕返回结果时T1才能往下执行泡茶
        FutureTask<String> futureTask1 = new FutureTask<>(new T1Task(futureTask3));

        Thread thread1 = new Thread(futureTask1);
        Thread thread2 = new Thread(futureTask2);
        Thread thread3 = new Thread(futureTask3);
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            System.out.println(futureTask1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
