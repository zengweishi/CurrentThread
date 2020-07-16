package com.concurrent.deadlock;

import java.util.ArrayList;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/9 09:40
 * @Description:管理临界区的角色，负责申请资源，释放资源
 */
public class Allocator {
    //用volatile禁止指令重排序，保证有序性，保证多线程情况下不会出现由于指令重排序导致的获取单例为空指针异常的情况
    private static volatile Allocator allocator;
    private ArrayList<Object> list = new ArrayList<>();
    /**
     * 创建单例
     */
    public static Allocator getInstance() {
        if (allocator == null) {
            synchronized (Allocator.class) {
                if (allocator == null) {
                    allocator = new Allocator();
                }
            }
        }
        return allocator;
    }
    /**
     * 申请资源
     */
    synchronized void apply(Object from, Object to) {
        if (list.contains(from) || list.contains(to)) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        list.add(from);
        list.add(to);
    }
    /**
     * 释放资源
     */
    synchronized void free(Object from, Object to) {
        list.remove(from);
        list.remove(to);
        notifyAll();
    }
}
