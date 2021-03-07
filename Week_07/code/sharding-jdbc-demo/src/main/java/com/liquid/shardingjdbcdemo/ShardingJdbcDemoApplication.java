package com.liquid.shardingjdbcdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sharding JDBC Demo启动类
 *
 * @author Liquid
 */
@SpringBootApplication
@MapperScan("com.liquid.shardingjdbcdemo.db.dao")
public class ShardingJdbcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcDemoApplication.class, args);
    }

}
