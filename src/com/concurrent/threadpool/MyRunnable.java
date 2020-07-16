package com.concurrent.threadpool;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/16 09:40
 * @Description:
 */
public class MyRunnable implements Runnable {
    /**
     * 运算结果
     */
    private RunResult runResult;
    /**
     * 线程同步器，用于线程等待，控制主线程中所有子线程都执行完
     */
    private CountDownLatch countDownLatch;
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name+"开始执行！");
        for (int j = 0;j <= 20000;j++) {
            if (j == 20000 && null != runResult.getParam()) {
                runResult.setResult(runResult.getParam()*10);
                runResult.setSuccess(true);
                System.out.println(name+"开始计算,参数："+runResult.getParam()+",结果："+runResult.getResult());
            }
        }
        System.out.println(name+"执行完毕！");
        //countDownLatch减一
        countDownLatch.countDown();
    }

    public MyRunnable(RunResult runResult, CountDownLatch countDownLatch) {
        super();
        this.runResult = runResult;
        this.countDownLatch = countDownLatch;
    }
    public MyRunnable() {
        super();
    }
}
