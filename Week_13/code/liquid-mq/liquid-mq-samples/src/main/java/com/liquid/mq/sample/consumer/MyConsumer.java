package com.liquid.mq.sample.consumer;

import com.liquid.mq.client.consumer.LiquidMqConsumer;
import com.liquid.mq.client.consumer.annotation.MqConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义consumer
 *
 * @author Liquid
 */
@MqConsumer(topic = "Test")
public class MyConsumer implements LiquidMqConsumer {
    private Logger logger = LoggerFactory.getLogger(MyConsumer.class);

    @Override
    public <T> void consume(T data) throws Exception {
        logger.info("consume a message:{}", data);
    }
}
