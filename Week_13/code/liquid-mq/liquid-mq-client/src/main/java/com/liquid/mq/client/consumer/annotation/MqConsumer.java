package com.liquid.mq.client.consumer.annotation;

import java.lang.annotation.*;

/**
 * 自定义消费者注解类
 *
 * @author Liquid
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MqConsumer {
    String topic() default "default";
}
