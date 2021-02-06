package com.liquid;

import java.util.concurrent.CountDownLatch;

/**
 * 为CountDownLatch封装MyRunnable
 *
 * @author Liquid
 */
public class MyRunnable6<T> implements Runnable {
    private Result<T> res = new Result<>();
    private CountDownLatch countDownLatch;

    @Override
    public void run() {
        res = new Result(sum());
        countDownLatch.countDown();
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

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}
