package com.liquid.kafkademo.queue;


import cn.hutool.json.JSONUtil;
import com.liquid.kafkademo.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class KafkaProducer {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage() throws InterruptedException {
        Message m = new Message();
        m.setId(System.currentTimeMillis());
        m.setMsg(UUID.randomUUID().toString());
        m.setSendTime(new Date());
        log.info("KafkaProducer生产了消息:{}", m.getMsg());
        kafkaTemplate.send("test", JSONUtil.toJsonStr(m));
    }
}
