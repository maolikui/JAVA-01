package com.liquid.rwseparate.dynamic;

import cn.hutool.core.collection.CollUtil;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机策略
 *
 * @author Liquid
 */
public class RandomRouter implements DataSourceRouter {

    /**
     * 简单的轮随机策略
     *
     * @param dsIds 数据源列表
     * @return
     */
    @Override
    public String route(List<String> dsIds) {
        return CollUtil.isEmpty(dsIds) ? null : dsIds.get(ThreadLocalRandom.current().nextInt(dsIds.size()));
    }
}
