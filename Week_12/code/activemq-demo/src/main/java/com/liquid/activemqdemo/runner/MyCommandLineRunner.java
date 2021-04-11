package com.liquid.activemqdemo.runner;

import com.liquid.activemqdemo.queue.QueueProducer;
import com.liquid.activemqdemo.topic.TopicPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * 服务启动后测试执行类
 *
 * @author Liquid
 */
@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {
    @Autowired
    private QueueProducer queueProducer;
    @Autowired
    private TopicPublisher topicPublisher;

    @Override
    public void run(String... args) throws Exception {
        // queueProducer.send();
        // queueProducer.send2();
        // topicPublisher.publish();
        topicPublisher.publish2();
    }
}
