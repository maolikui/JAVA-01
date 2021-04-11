package com.liquid.activemqdemo.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Queue producer
 *
 * @author Liquid
 */
@Service
public class QueueProducer {

    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final Queue queue;
    private final ActiveMQConnectionFactory connectionFactory;

    @Autowired
    public QueueProducer(JmsMessagingTemplate jmsMessagingTemplate, Queue queue, ActiveMQConnectionFactory connectionFactory) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.queue = queue;
        this.connectionFactory = connectionFactory;
    }

    /**
     * spring framework jms
     */
    public void send() {
        for (int i = 0; i < 10; i++) {
            jmsMessagingTemplate.convertAndSend(queue, "queue" + i);
        }
    }

    /**
     * jms
     */
    public void send2() {
        Connection connection = null;
        try {
            //获取Connection
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建会话
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //创建消息发送者
            MessageProducer producer = session.createProducer(queue);
            //发送消息
            for (int i = 0; i < 10; i++) {
                TextMessage message = session.createTextMessage("jms queue" + i);
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
