package com.liquid.mq.server;

import com.liquid.mq.client.repository.MqRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Mq server启动类
 *
 * @author Liquid
 */
@SpringBootApplication
public class LiquidMqServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiquidMqServerApplication.class, args);
    }

    @Bean
    MqRepository mqRepository() {
        return new MqRepository();
    }
}
