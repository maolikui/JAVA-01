package com.liquid;

import java.util.concurrent.Callable;

/**
 * 封装自定义Callable类
 *
 * @author Liquid
 */
public class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return sum();
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
