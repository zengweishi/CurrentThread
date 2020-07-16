package com.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/16 10:10
 * @Description:
 */
public class CallableTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor =  new ThreadPoolExecutor(12, 20, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(20),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        //存放Future<RunResult>的集合
        List<Future<RunResult>> list = new ArrayList<>();
        //线程同步器
        CountDownLatch countDownLatch = new CountDownLatch(12);
        for (int i = 0;i < 12;i++) {
            RunResult result = new RunResult();
            result.setParam(i);
            MyCallable myCallable = new MyCallable(result,countDownLatch);
            Future<RunResult> future = executor.submit(myCallable);
            list.add(future);
        }
        try {
            //线程等待，等待所有线程都执行完成
            countDownLatch.await();
            ArrayList<RunResult> arrayList = new ArrayList<>();
            for (Future<RunResult> result : list) {
                arrayList.add(result.get());
            }
            System.out.println(toString(arrayList));
        } catch (Exception e) {
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
