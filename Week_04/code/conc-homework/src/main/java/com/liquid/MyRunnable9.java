package com.liquid;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 为Volaitail封装MyRunnable
 *
 * @author Liquid
 */
public class MyRunnable9<T> implements Runnable {
    private Result res = new Result<>();
    private volatile AtomicInteger signal = new AtomicInteger(1);

    @Override
    public void run() {
        res = new Result(sum());
        signal.decrementAndGet();
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

    public AtomicInteger getSignal() {
        return signal;
    }

    public void setSignal(AtomicInteger signal) {
        this.signal = signal;
    }
}
