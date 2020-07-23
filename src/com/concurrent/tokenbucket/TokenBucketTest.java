package com.concurrent.tokenbucket;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/23 11:11
 * @Description:Guava实现令牌桶算法
 * Guava 实现令牌桶算法，用了一个很简单的办法，其关键是记录并动态计算下一令牌发放的时间
 */
public class TokenBucketTest {
    //当前令牌桶中的令牌数量
    private long num;
    //令牌桶的容量
    private final long maxNum = 3;
    //下一个令牌产生的时间
    private long next = System.nanoTime();
    //发放令牌时间间隔
    private long interval = 1000_000_000;

    /**
     * 申请令牌
     */
    public void accquire() {
        //获取当前时间
        long now = System.nanoTime();
        //获取可以获取令牌的时间
        long getToken = reserve(now);
        //判断当前时间与获取令牌时间的先后关系
        if (getToken > now) {
            try {
                TimeUnit.NANOSECONDS.sleep(getToken-now);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 预占令牌，返回能够获取令牌的时间
     * @param now
     * @return
     */
    private synchronized long reserve(long now) {
        //now>next:计算next的值;now<=next 取next的值
        if (now > next) {
            //计算时间间隔内允许新增的令牌数
            Integer newAdd = Math.toIntExact((now - next) / interval);
            //更新令牌桶数量
            if (maxNum > (num + newAdd)) {
                num += newAdd;
            } else {
                num = maxNum;
            }
            //设置当前时间为获取令牌时间
            next = now;
        }
        long lat = next;
        //如果令牌桶中能提供令牌，那么next保持不变；如果此时令牌桶中不能提供令牌，那么next = next + nr*interval
        //假设now = 3,next = 0,那么执行完后next=3,num=2
        //1s之后，now = 4,next = 3,此时10个请求过来，假设一个请求时间极短，这些请求都在1s内执行
        //第1个请求执行，执行前：now=4,next=3,now-next=1执行后：now=4,next=4,num=2+1=3,后面-1=2，得到令牌桶的令牌
        //第2个请求执行,执行前：now=4,next=4,now=next执行后：now=4,next=4,num=2-1=1，得到令牌桶的令牌
        //第3个请求执行，执行前：now=4,next=4,now=next执行后：now=4,next=4,num=1-1=0，得到令牌桶的令牌
        //第4个请求执行，执行前：now=4,next=4,lat=4执行后：now=4,next+1=5,num=0-0=0，等待第lat秒生成的令牌
        //第5请求执行，执行前：now=4,next=5,lat=5执行后：now=4,next+1=6,num=0-0=0，等待第lat秒生成的令牌
        //......
        //第10请求执行，执行前：now=4,next=10,lat=10执行后：now=4,next+1=11,num=0-0=0，等待第lat秒生成的令牌
        //重新计算下一次令牌生成时间nex
        long fb = getMin(1,num);
        long nr = 1 - fb;
        next = next + nr*interval;
        //重新计算令牌桶的令牌数量
        this.num -= fb;
        return lat;
    }

    private long getMin(int i, long num) {
        if (i > num) {
            return num;
        }
        return i;
    }
}
