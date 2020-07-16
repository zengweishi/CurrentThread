package com.concurrent.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/13 14:31
 * @Description:
 */
public class CountDownLatchTest {
    Integer p = null;
    Integer q = null;
    public void test() throws InterruptedException {
        Executor executor = Executors.newFixedThreadPool(2);
        while(true) {
            //计数器初始化为2
            CountDownLatch countDownLatch = new CountDownLatch(2);
            //查询未对账订单
            executor.execute(() -> {
                p = getPOrders();
                countDownLatch.countDown(); //减1

            });
            //查询派送单
            executor.execute(() -> {
                q = getDOrders();
                countDownLatch.countDown(); //减1
            });

            //等待2个线程结束
            countDownLatch.await();

            //对账
            Integer excep = check(p,q);
            //异常账单入库
            save(excep);
        }
    }

    private void save(Integer excep) {
    }

    private Integer check(Integer p, Integer q) {
        return 1;
    }

    private Integer getDOrders() {
        return 1;
    }

    private Integer getPOrders() {
        return 1;
    }
}
