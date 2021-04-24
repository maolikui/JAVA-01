package com.liquid.mq.client.consumer.Thread;

import com.liquid.mq.client.consumer.LiquidMqConsumer;
import com.liquid.mq.client.consumer.annotation.MqConsumer;
import com.liquid.mq.client.factory.LiquidMqClientFactory;
import com.liquid.mq.client.message.LiquidMqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * consumer thread
 *
 * @author Liquid
 */
public class ConsumerThread extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(ConsumerThread.class);
    private LiquidMqConsumer consumerHandler;
    private MqConsumer mqConsumer;


    public ConsumerThread(LiquidMqConsumer consumerHandler) {
        this.consumerHandler = consumerHandler;
        this.mqConsumer = consumerHandler.getClass().getAnnotation(MqConsumer.class);
    }

    public MqConsumer getMqConsumer() {
        return mqConsumer;
    }

    @Override
    public void run() {
        while (!LiquidMqClientFactory.clientFactoryPoolStoped) {
            try {
                // pull message
                List<LiquidMqMessage> messageList = LiquidMqClientFactory.getLiquidMqBroker().pull(mqConsumer.topic());
                if (messageList != null && messageList.size() > 0) {
                    for (final LiquidMqMessage msg : messageList) {
                        // consume message
                        consumerHandler.consume(msg.getBody());
                    }
                }
            } catch (Exception e) {
                if (!LiquidMqClientFactory.clientFactoryPoolStoped) {
                    logger.error(e.getMessage(), e);
                }
            }
            //sleep 5 seconds
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

