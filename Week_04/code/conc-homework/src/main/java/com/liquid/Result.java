package com.liquid;

/**
 * 封装响应结果类
 *
 * @param <T>
 * @author Liquid
 */
public class Result<T> {
    private T res;

    public Result() {
    }

    public T getRes() {
        return res;
    }

    public void setRes(T res) {
        this.res = res;
    }

    public Result(T res) {
        this.res = res;
    }
}
