package com.liquid;

/**
 * 为wait/notify封装MyRunnable
 *
 * @author Liquid
 */
public class MyRunnable2<T> implements Runnable {
    private Result<T> res = new Result<>();
    private boolean flag = true;
    private Thread t;

    @Override
    public void run() {
        while (flag) {
            synchronized (this) {
                res = new Result(sum());
                // notifyAll();
                t.interrupt();
                flag = false;
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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }
}
