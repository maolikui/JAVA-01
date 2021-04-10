package com.liquid.activemqdemo.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Topic subscriber
 *
 * @author Liquid
 */
@Component
@Slf4j
public class TopicSubscriber {

    @JmsListener(destination = "test.topic",containerFactory = "jmsListenerContainerTopic")
    public void receive(String msg) {
        log.info("Topic subscriber receive msg: " + msg);
    }

}
