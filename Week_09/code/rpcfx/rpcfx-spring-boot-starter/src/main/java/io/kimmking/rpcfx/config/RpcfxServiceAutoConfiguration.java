package io.kimmking.rpcfx.config;

import io.kimmking.rpcfx.DefaultResolver;
import io.kimmking.rpcfx.ZooKeeperServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * Rpcfx Service自动配置类
 *
 * @author Liquid
 */
@Configuration
@EnableConfigurationProperties({ZookeeperProperties.class, RpcfxProperties.class})
public class RpcfxServiceAutoConfiguration {
    @Autowired
    private ZookeeperProperties zookeeperProperties;
    @Autowired
    private RpcfxProperties rpcfxProperties;


    @Bean
    public ZooKeeperServiceRegistry zooKeeperServiceRegistry() {
        return new ZooKeeperServiceRegistry(zookeeperProperties.getAddress());
    }

    @Bean
    public DefaultResolver defaultResolver(@Qualifier("zooKeeperServiceRegistry") ZooKeeperServiceRegistry zooKeeperServiceRegistry) throws Exception {
        return new DefaultResolver(InetAddress.getLocalHost().getHostAddress() + ":" + rpcfxProperties.getPort(), zooKeeperServiceRegistry);
    }
}
