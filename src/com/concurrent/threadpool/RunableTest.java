package com.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/16 09:41
 * @Description:
 */
public class RunableTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor =  new ThreadPoolExecutor(12, 20, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(20),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        //结果集合
        ArrayList<RunResult> list  = new ArrayList<>();
        //线程同步器，用于线程等待，所有子线程都执行完毕再往下执行
        CountDownLatch countDownLatch = new CountDownLatch(12);
        for (int i = 0; i < 12;i++) {
            RunResult result = new RunResult();
            result.setParam(i);
            list.add(result);
            MyRunnable myRunnable = new MyRunnable(result, countDownLatch);
            //线程执行
            executor.execute(myRunnable);
        }
        try {
            //线程等待，等待12个线程执行完毕
            countDownLatch.await();
            System.out.println(toString(list));
            //关闭线程池
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String toString(List<RunResult> list) {
        String str = new String();
        for (RunResult result : list) {
            String s = "[参数:"+result.getParam()+",结果:"+result.getResult()+",成功:"+result.getSuccess()+"]";
            str+=s;
        }
        return str;
    }
}
