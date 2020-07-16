package com.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/16 16:18
 * @Description: T2:洗水壶（5分钟）
 */
public class T2Task implements Callable {
    @Override
    public String call() throws Exception {
        System.out.println("T2 开始洗水壶 5s");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("T2 洗水壶结束");
        return "小米热水壶";
    }
}
