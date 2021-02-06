package com.liquid;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 为ReentrantLock/Condition封装MyRunnable
 *
 * @author Liquid
 */
public class MyRunnable4<T> implements Runnable {
    private Result<T> res = new Result<>();
    private boolean flag = true;
    private ReentrantLock lock;
    private Condition notCompleted;

    @Override
    public void run() {
        lock.lock();
        if (flag) {
            res = new Result(sum());
            flag = false;
            notCompleted.signal();
        }
        lock.unlock();
    }


    private int sum() {
        return fibo(36);
    }

    private int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

    public Result<T> getRes() {
        return res;
    }

    public void setRes(Result<T> res) {
        this.res = res;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void setLock(ReentrantLock lock) {
        this.lock = lock;
    }

    public Condition getNotCompleted() {
        return notCompleted;
    }

    public void setNotCompleted(Condition notCompleted) {
        this.notCompleted = notCompleted;
    }
}
