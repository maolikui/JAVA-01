package com.liquid.activemqdemo.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Queue consumer
 *
 * @author Liquid
 */
@Component
@Slf4j
public class QueueConsumer {
    @JmsListener(destination = "test.queue",containerFactory = "jmsListenerContainerQueue")
    public void receiveMessage(String message) {
        log.info("Queue consumer receive msg: " + message);
    }
}
