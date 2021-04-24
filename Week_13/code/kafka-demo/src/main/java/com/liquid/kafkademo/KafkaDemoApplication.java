package com.liquid.kafkademo;

import com.liquid.kafkademo.queue.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class KafkaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApplication.class, args);
    }

    @Autowired
    KafkaProducer kafkaProducer;

    @Bean
    ApplicationRunner runner() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                for (int i = 0; i < 100; i++) {
                    kafkaProducer.sendMessage();
                    TimeUnit.SECONDS.sleep(5);
                }
            }
        };
    }
}
