package com.liquid;

import java.util.concurrent.Exchanger;

/**
 * 为Exchanger封装MyRunnable
 *
 * @author Liquid
 */
public class MyRunnable8<T> implements Runnable {
    private Result res = new Result<>();
    private Exchanger<Result<Integer>> exchanger;

    @Override
    public void run() {
        res = new Result(sum());
        try {
            exchanger.exchange(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public Exchanger<Result<Integer>> getExchanger() {
        return exchanger;
    }

    public void setExchanger(Exchanger<Result<Integer>> exchanger) {
        this.exchanger = exchanger;
    }
}
