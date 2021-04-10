package com.liquid.activemqdemo.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Topic;

/**
 * Topic publisher
 *
 * @author Liquid
 */
@Service
public class TopicPublisher {
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final Topic topic;

    @Autowired
    public TopicPublisher(JmsMessagingTemplate jmsMessagingTemplate, Topic topic) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.topic = topic;
    }

    public void publish() {
        for (int i = 0; i < 10; i++) {
            jmsMessagingTemplate.convertAndSend(topic, "topic" + i);
        }
    }
}
