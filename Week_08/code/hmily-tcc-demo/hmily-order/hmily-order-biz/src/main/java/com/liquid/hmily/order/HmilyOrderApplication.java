package com.liquid.hmily.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 订单系统
 *
 * @author Liquid
 */
@EnableFeignClients(basePackages = "com.liquid.hmily")
@EnableDiscoveryClient
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class HmilyOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmilyOrderApplication.class, args);
    }
}
