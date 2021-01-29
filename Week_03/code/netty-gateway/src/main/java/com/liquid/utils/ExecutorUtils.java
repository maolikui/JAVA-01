package com.liquid.utils;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.util.ObjectUtil;
import com.liquid.inbound.NamedThreadFactory;

import java.util.concurrent.*;

/**
 * 线程池工具类
 *
 * @author Liquid
 */
public class ExecutorUtils {
    private static volatile ExecutorUtils executorUtils;
    private static ExecutorService executor;

    private ExecutorUtils() {
        executor = ExecutorBuilder.create()
                .setCorePoolSize(Runtime.getRuntime().availableProcessors() * 2)
                .setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2)
                .setKeepAliveTime(1, TimeUnit.MINUTES)
                .setWorkQueue(new ArrayBlockingQueue<>(2048))
                .setThreadFactory(new NamedThreadFactory("proxyService"))
                .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .build();
    }

    public static ExecutorUtils getInstance() {
        if (ObjectUtil.isNull(executorUtils)) {
            synchronized (ExecutorUtils.class) {
                if (ObjectUtil.isNull(executorUtils)) {
                    executorUtils = new ExecutorUtils();
                }
            }
        }
        return executorUtils;
    }

    public void submit(Runnable task) {
        executor.submit(task);
    }

    public Executor getExecutor() {
        return executor;
    }
}
