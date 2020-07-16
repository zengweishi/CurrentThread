package com.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/16 16:18
 * @Description: T1:洗茶壶（1分钟） 洗茶杯（2分钟） 拿茶叶（1分钟）泡茶
 */
public class T1Task implements Callable {
    /**
     * futureTask3，保存的是T3的开水
     */
    private FutureTask<String> futureTask3;

    public void setFutureTask3(FutureTask<String> futureTask3) {
        this.futureTask3 = futureTask3;
    }

    public T1Task(FutureTask<String> futureTask3) {
        this.futureTask3 = futureTask3;
    }

    @Override
    public String call() throws Exception {
        System.out.println("T1 开始洗茶壶 1s");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("T1 开始洗茶杯 2s");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("T1 开始拿茶叶 1s");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("T1 尝试拿开水");
        String water = futureTask3.get();//拿不到结果则阻塞，等待task3执行完成返回结果
        System.out.println("T1 拿到开水："+water);
        System.out.println("T1 泡茶");
        return "上茶：千年龙井";
    }
}
