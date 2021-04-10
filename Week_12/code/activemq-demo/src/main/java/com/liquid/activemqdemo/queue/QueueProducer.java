package com.liquid.activemqdemo.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

/**
 * Queue producer
 *
 * @author Liquid
 */
@Service
public class QueueProducer {

    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final Queue queue;

    @Autowired
    public QueueProducer(JmsMessagingTemplate jmsMessagingTemplate, Queue queue) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.queue = queue;
    }

    public void send() {
        for (int i = 0; i < 10; i++) {
            jmsMessagingTemplate.convertAndSend(queue, "queue" + i);
        }
    }

}
