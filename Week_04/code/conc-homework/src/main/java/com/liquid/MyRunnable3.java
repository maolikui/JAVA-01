package com.liquid;

import java.util.concurrent.locks.LockSupport;

/**
 * 为park/unpark封装MyRunnable
 *
 * @author Liquid
 */
public class MyRunnable3<T> implements Runnable {
    private Result<T> res = new Result<>();
    private Thread t;

    @Override
    public void run() {
        res = new Result(sum());
        LockSupport.unpark(t);
        // t.interrupt();
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

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }
}
