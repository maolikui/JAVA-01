package com.liquid.mq.client.producer;

import com.liquid.mq.client.factory.LiquidMqClientFactory;
import com.liquid.mq.client.message.LiquidMqMessage;

import java.util.List;

/**
 * Mq producer
 *
 * @author Liquid
 */
public class LiquidMqProducer {

    private LiquidMqClientFactory liquidMqClientFactory;


    /**
     * 生产消息
     *
     * @param mqMessage
     */
    public void produce(List<LiquidMqMessage> mqMessage) {
        liquidMqClientFactory.send(mqMessage);
    }
    /**
     * 生产消息
     *
     * @param mqMessage
     */
    public void produce(LiquidMqMessage mqMessage) {
        liquidMqClientFactory.send(mqMessage);
    }

    public void setLiquidMqClientFactory(LiquidMqClientFactory liquidMqClientFactory) {
        this.liquidMqClientFactory = liquidMqClientFactory;
    }
}
