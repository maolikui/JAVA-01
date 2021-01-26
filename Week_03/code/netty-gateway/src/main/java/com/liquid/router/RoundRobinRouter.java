package com.liquid.router;

import cn.hutool.core.collection.CollUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询策略
 *
 * @author Liquid
 */
public class RoundRobinRouter implements HttpEndpointRouter {
    private final AtomicInteger idx = new AtomicInteger();

    /**
     * 简单的轮询策略
     *
     * @param endpoints 服务列表
     * @return
     */
    @Override
    public String route(List<String> endpoints) {
        return CollUtil.isEmpty(endpoints) ? null : endpoints.get(Math.abs(idx.getAndIncrement() % endpoints.size()));
    }
}
