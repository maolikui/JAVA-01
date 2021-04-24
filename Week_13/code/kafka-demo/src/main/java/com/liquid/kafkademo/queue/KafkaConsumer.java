package com.liquid.kafkademo.queue;

import cn.hutool.json.JSONUtil;
import com.liquid.kafkademo.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = {"test"})
    public void processMessage(String content) {
        Message m = JSONUtil.toBean(content, Message.class);
        log.info("KafkaConsumer消费了消息:{}", m.getMsg());
    }
}
