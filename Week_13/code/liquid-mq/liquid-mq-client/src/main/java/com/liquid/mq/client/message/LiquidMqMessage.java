package com.liquid.mq.client.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

/**
 * 消息实体类
 *
 * @param <T>
 * @author Liquid
 */
@AllArgsConstructor
@Data
public class LiquidMqMessage<T> {

    private HashMap<String, Object> headers;
    private String topic;
    private T body;
}
