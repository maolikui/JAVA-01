package com.liquid.mq.client.factory;

import com.liquid.mq.client.broker.LiquidMqBroker;
import com.liquid.mq.client.consumer.LiquidMqConsumer;
import com.liquid.mq.client.consumer.Thread.ConsumerThread;
import com.liquid.mq.client.consumer.annotation.MqConsumer;
import com.liquid.mq.client.message.LiquidMqMessage;
import com.liquid.mq.client.utils.MyThreadUtil;
import io.kimmking.rpcfx.client.RpcfxProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 发送消息至远程server
 *
 * @author Liquid
 */
public class LiquidMqClientFactory {
    private final static Logger logger = LoggerFactory.getLogger(LiquidMqClientFactory.class);

    private String serviceAddr;
    private List<LiquidMqConsumer> consumerList;
    private List<ConsumerThread> consumerRespository = new ArrayList<ConsumerThread>();
    public static volatile boolean clientFactoryPoolStoped = false;
    private static LiquidMqBroker liquidMqBroker;

    public LiquidMqClientFactory() {
    }

    public void init() {
        //初始化配置的consumer
        initConsumer();
        //初始化broker
        initBrokerService();
        //启动consumer执行线程
        startConsumer();
    }

    private void initConsumer() {
        if (consumerList == null || consumerList.size() == 0) {
            return;
        }
        for (LiquidMqConsumer consumer : consumerList) {
            MqConsumer annotation = consumer.getClass().getAnnotation(MqConsumer.class);
            if (annotation == null) {
                return;
            }
            if (annotation.topic() == null || annotation.topic().trim().length() == 0) {
                return;
            }
            consumerRespository.add(new ConsumerThread(consumer));
        }
    }

    public void initBrokerService() {
        // init liquidMqBroker
        RpcfxProxy proxy = new RpcfxProxy(serviceAddr);
        liquidMqBroker = proxy.create(LiquidMqBroker.class);
    }

    private void startConsumer() {
        if (consumerRespository == null || consumerRespository.size() == 0) {
            return;
        }
        // execute consumer thread
        for (ConsumerThread c : consumerRespository) {
            MyThreadUtil.getInstance().execute(c);
        }
    }

    public void send(List<LiquidMqMessage> mqMessage) {
        // ZooKeeperServiceAgent serviceAgent = new ZooKeeperServiceAgent("192.168.19.130:2181");
        RpcfxProxy proxy = new RpcfxProxy(serviceAddr);
        LiquidMqBroker liquidMqBroker = proxy.create(LiquidMqBroker.class);
        liquidMqBroker.send(mqMessage);
    }

    public void send(LiquidMqMessage mqMessage) {
        // ZooKeeperServiceAgent serviceAgent = new ZooKeeperServiceAgent("192.168.19.130:2181");
        RpcfxProxy proxy = new RpcfxProxy(serviceAddr);
        LiquidMqBroker liquidMqBroker = proxy.create(LiquidMqBroker.class);
        liquidMqBroker.send2(mqMessage);
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    public static LiquidMqBroker getLiquidMqBroker() {
        return liquidMqBroker;
    }
}
