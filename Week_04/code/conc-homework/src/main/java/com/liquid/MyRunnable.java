package com.liquid;

/**
 * 封装自定义Runnable类
 *
 * @param <T>
 */
public class MyRunnable<T> implements Runnable {
    private Result<T> res = new Result<>();

    public void run() {
        res = new Result(sum());
    }

    public Result<T> getRes() {
        return res;
    }

    private int sum() {
        return fibo(36);
    }

    private int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

}
