package com.concurrent.deadlock;

/**
 * @Auther: weishi.zeng
 * @Date: 2020/7/9 09:52
 * @Description:
 */
public class Account {
    //accocator应该为单例，只能由一个人来分配资源
    private Allocator allocator = Allocator.getInstance();
    private int id;
    private int balance;

    public Account() {
    }

    /**
     * 破环占用且等待条件
     * @param target
     * @param amt
     */
    public void transfer1(Account target,int amt) {
        //申请资源
        allocator.apply(this,target);
        try {
            //锁定转出账户
            synchronized (this) {
                //锁定转入账户
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            allocator.free(this,target);
        }
    }

    /**
     * 破环循环且等待条件
     */
    public void transfer2(Account target, int amt) {
        Account left = this;
        Account right = target;
        //先对2个资源按照id大小进行排序，然后根据id大小按顺序锁定资源
        if (left.id > right.id) {
            left = target;
            right = this;
        }
        //锁定id小的账户
        synchronized (left) {
            //锁定id大的账户
            synchronized (right) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}
