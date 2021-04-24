package com.liquid.mq.server;

import com.liquid.mq.client.broker.LiquidMqBroker;
import com.liquid.mq.client.message.LiquidMqMessage;
import com.liquid.mq.client.repository.LiquidMqQueue;
import com.liquid.mq.client.repository.MqRepository;
import io.kimmking.rpcfx.annotation.RpcfxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * RPC server 实现类
 *
 * @author Liquid
 */
@RpcfxService(LiquidMqBroker.class)
@Slf4j
public class LiquidMqBrokerService implements LiquidMqBroker {
    @Autowired
    MqRepository mqRepository;

    public int send(List<LiquidMqMessage> mqMessage) {
        //client push message
        log.info("push mqMessage:" + mqMessage);
        //按照topic存储客户端发送的消息
        //可以选择数据库，文件，本例为了简化使用n

        return 1;
    }

    public int send2(LiquidMqMessage mqMessage) {
        //client push message
        log.info("push mqMessage:" + mqMessage);
        //按照topic存储客户端发送的消息
        //可以选择数据库，文件，本例为了简化使用内存简化
        mqRepository.createTopic(mqMessage.getTopic());
        LiquidMqQueue queue = mqRepository.findLiquidMqQueue(mqMessage.getTopic());
        queue.save(mqMessage);
        return 0;
    }

    public List<LiquidMqMessage> pull(String topic) {
        //client pull message
        log.info("pull message!");
        LiquidMqQueue queue = mqRepository.findLiquidMqQueue(topic);
        return Arrays.asList(queue.poll());
    }
}
