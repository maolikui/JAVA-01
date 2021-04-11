package com.liquid.activemqdemo.topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Topic publisher
 *
 * @author Liquid
 */
@Service
public class TopicPublisher {
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final Topic topic;
    private final ActiveMQConnectionFactory connectionFactory;

    @Autowired
    public TopicPublisher(JmsMessagingTemplate jmsMessagingTemplate, Topic topic, ActiveMQConnectionFactory connectionFactory) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.topic = topic;
        this.connectionFactory = connectionFactory;
    }

    /**
     * spring framework jms
     */
    public void publish() {
        for (int i = 0; i < 10; i++) {
            jmsMessagingTemplate.convertAndSend(topic, "topic" + i);
        }
    }

    /**
     * jms
     */
    public void publish2() {
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(topic);
            for (int i = 0; i < 10; i++) {
                TextMessage message = session.createTextMessage("jms topic" + i);
                producer.send(message);
            }
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
