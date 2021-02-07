package com.liquid;

import java.io.IOException;
import java.io.PipedWriter;

/**
 * 为pipe封装MyRunnable
 *
 * @author Liquid
 */
public class MyRunnable10<T> implements Runnable {
    private Result res = new Result<>();
    private PipedWriter writer;

    public MyRunnable10(PipedWriter writer) {
        this.writer = writer;
    }

    @Override
    public void run() {
        res = new Result(sum());
        try {
            writer.write(Integer.toString((Integer) res.getRes()).toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}
