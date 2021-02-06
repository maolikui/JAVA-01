package com.liquid;

import java.util.concurrent.Semaphore;

/**
 * 为Semaphore封装MyRunnable
 *
 * @author Liquid
 */
public class MyRunnable5<T> implements Runnable {
    private Result<T> res = new Result<>();
    private Semaphore notCompleted;

    @Override
    public void run() {
        res = new Result(sum());
        notCompleted.release();
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

    public Semaphore getNotCompleted() {
        return notCompleted;
    }

    public void setNotCompleted(Semaphore notCompleted) {
        this.notCompleted = notCompleted;
    }
}
