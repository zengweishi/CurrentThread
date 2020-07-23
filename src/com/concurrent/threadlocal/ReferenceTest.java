package com.concurrent.threadlocal;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/20 15:57
 * @Description:强引用/弱引用/软引用/虚引用 测试
 */
public class ReferenceTest {
    public static void main(String[] args) {
        //强引用：当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不回收这种对象。
        Object object = new Object();
        String str = "StrongReference";

        //软引用：只有在内存不足的时候JVM才会回收该对象
        System.out.println("软引用===>:");
        System.out.println("start");
        Integer obj = new Integer(1);
        SoftReference<Integer> reference = new SoftReference<Integer>(obj);
        obj = null;
        System.out.println(reference.get());
        System.out.println("end");


        //弱引用：当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象
        System.out.println("弱引用===>：");
        WeakReference<String> sr = new WeakReference<String>(new String("hello"));
        System.out.println(sr.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println(sr.get());

        //虚引用：如果一个对象与虚引用关联，则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收。虚引用主要用来跟踪对象被垃圾回收的活动
        System.out.println("虚引用===>：");
        ReferenceQueue<String> queue = new ReferenceQueue<String>();//必须和引用队列关联使用
        PhantomReference<String> pr = new PhantomReference<String>(new String("hello"), queue);
        System.out.println(pr.get());
    }
}
