package com.liquid.mq.sample;

import com.liquid.mq.client.consumer.LiquidMqConsumer;
import com.liquid.mq.client.message.LiquidMqMessage;
import com.liquid.mq.client.producer.LiquidMqProducer;
import com.liquid.mq.sample.conf.LiquidMqConf;
import com.liquid.mq.sample.consumer.MyConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用例测试类
 *
 * @author Liquid
 */
public class SampleApplication {
    public static void main(String[] args) {
        List<LiquidMqConsumer> consumerList = new ArrayList<>();
        consumerList.add(new MyConsumer());

        LiquidMqConf.getInstance().start(consumerList);

        LiquidMqProducer mqProducer = new LiquidMqProducer();
        mqProducer.setLiquidMqClientFactory(LiquidMqConf.getInstance().getMqClientFactory());
        for (int i = 0; i < 10; i++) {
            LiquidMqMessage<String> message = new LiquidMqMessage(null, "Test", "test message: " + i);
            mqProducer.produce(message);
        }

        try {
            TimeUnit.MINUTES.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
