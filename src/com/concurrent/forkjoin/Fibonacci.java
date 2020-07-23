package com.concurrent.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/17 16:27
 * @Description:递归任务
 */
public class Fibonacci extends RecursiveTask<Integer> {
    final int n;
    Fibonacci(int n){this.n = n;}
    protected Integer compute(){
        if (n <= 1)
            return n;
        Fibonacci f1 = new Fibonacci(n - 1);
        // 创建子任务
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2);
        //  join() 方法会阻塞当前线程来等待子任务的执行结果，并合并结果
        System.out.println(f2.compute() + f1.join());
        return f2.compute() + f1.join();
    }
}
