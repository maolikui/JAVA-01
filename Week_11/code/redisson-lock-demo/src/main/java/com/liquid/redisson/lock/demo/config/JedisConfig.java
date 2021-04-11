package com.liquid.redisson.lock.demo.config;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Jedis client 配置
 *
 * @author Liquid
 */
@Configuration
public class JedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;


    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Optional.ofNullable(maxIdle).orElse(1000));
        config.setMaxWaitMillis(Optional.ofNullable(maxWaitMillis).orElse(2000L));
        JedisPool jedisPool = null;
        if (StringUtils.isEmpty(password)) {
            jedisPool = new JedisPool(config,
                    Optional.ofNullable(host).orElse("localhost"),
                    Optional.ofNullable(port).orElse(6379),
                    Optional.ofNullable(timeout).orElse(100000));
        } else {
            jedisPool = new JedisPool(config,
                    Optional.ofNullable(host).orElse("localhost"),
                    Optional.ofNullable(port).orElse(6379),
                    Optional.ofNullable(timeout).orElse(100000), password);
        }
        return jedisPool;
    }
}
