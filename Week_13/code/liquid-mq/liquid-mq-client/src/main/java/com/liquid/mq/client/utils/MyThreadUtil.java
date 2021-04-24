package com.liquid.mq.client.utils;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.ExecutorService;

/**
 * 自定义线程池
 *
 * @author Liquid
 */
public class MyThreadUtil {
    private MyThreadUtil() {
    }

    private static class MyThreadPool {
        private static ExecutorService INSTANCE = ThreadUtil.newExecutor(Runtime.getRuntime().availableProcessors() * 2, Runtime.getRuntime().availableProcessors() * 2);
    }

    public static ExecutorService getInstance() {
        return MyThreadPool.INSTANCE;
    }
}
