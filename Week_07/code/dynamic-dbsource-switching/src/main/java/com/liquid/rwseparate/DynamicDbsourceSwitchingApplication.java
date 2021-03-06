package com.liquid.rwseparate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 读写项目分离启动类
 *
 * @author Liquid
 */
@SpringBootApplication
public class DynamicDbsourceSwitchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicDbsourceSwitchingApplication.class, args);
    }

}
