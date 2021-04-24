package com.liquid.mq.client.repository;

import com.liquid.mq.client.message.LiquidMqMessage;
import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Queue
 * @author Liquid
 */
public class LiquidMqQueue {
    public LiquidMqQueue(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue(capacity);
    }

    private String topic;

    private int capacity;

    private LinkedBlockingQueue<LiquidMqMessage> queue;

    public boolean save(LiquidMqMessage message) {
        return queue.offer(message);
    }

    public LiquidMqMessage poll() {
        return queue.poll();
    }

    @SneakyThrows
    public LiquidMqMessage poll(long timeout) {
        return queue.poll(timeout, TimeUnit.MILLISECONDS);
    }
}
