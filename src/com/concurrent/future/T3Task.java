package com.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/16 16:46
 * @Description: T3:烧水（15分钟）
 */
public class T3Task implements Callable {
    private FutureTask<String> futureTask2;

    public FutureTask<String> getFutureTask2() {
        return futureTask2;
    }

    public T3Task(FutureTask<String> futureTask2) {
        this.futureTask2 = futureTask2;
    }

    @Override
    public Object call() throws Exception {
        System.out.println("T3 尝试拿水壶");
        String cup = futureTask2.get();//拿不到结果则阻塞，等待task2执行完成返回结果
        System.out.println("T3 拿到热水壶：" + cup);
        System.out.println("T3 开始烧水 10s");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("T3 烧水结束");
        return "昆仑山矿泉水烧的开水";
    }
}
