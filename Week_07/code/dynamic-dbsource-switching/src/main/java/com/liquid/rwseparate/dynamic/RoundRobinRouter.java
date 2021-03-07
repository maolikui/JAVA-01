package com.liquid.rwseparate.dynamic;

import cn.hutool.core.collection.CollUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询策略
 *
 * @author Liquid
 */
public class RoundRobinRouter implements DataSourceRouter {
    private final AtomicInteger idx = new AtomicInteger();

    /**
     * 简单的轮询策略
     *
     * @param dsIds 数据源列表
     * @return
     */
    @Override
    public String route(List<String> dsIds) {
        return CollUtil.isEmpty(dsIds) ? null : dsIds.get(Math.abs(idx.getAndIncrement() % dsIds.size()));
    }
}
