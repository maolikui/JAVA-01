package com.liquid.mq.client.consumer;

/**
 * Mq consumer
 *
 * @author Liquid
 */
public interface LiquidMqConsumer {

    /**
     * consume message
     *
     * @param data
     * @return
     * @throws Exception
     */
    <T> void consume(T data) throws Exception;
}
