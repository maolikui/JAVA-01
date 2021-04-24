package com.liquid.mq.client.broker;

import com.liquid.mq.client.message.LiquidMqMessage;

import java.util.List;

/**
 * RPC访问api定义类
 *
 * @author Liquid
 */
public interface LiquidMqBroker {

    int send(List<LiquidMqMessage> mqMessage);

    int send2(LiquidMqMessage mqMessage);

    List<LiquidMqMessage> pull(String topic);
}
