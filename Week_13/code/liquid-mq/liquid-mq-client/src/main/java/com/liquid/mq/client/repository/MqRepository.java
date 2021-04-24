package com.liquid.mq.client.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Respository
 *
 * @author Liquid
 */
public final class MqRepository {
    public static final int CAPACITY = 10000;

    private final Map<String, LiquidMqQueue> liquidMqQueueMapMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name) {
        liquidMqQueueMapMap.putIfAbsent(name, new LiquidMqQueue(name, CAPACITY));
    }

    public LiquidMqQueue findLiquidMqQueue(String topic) {
        return this.liquidMqQueueMapMap.get(topic);
    }
}
