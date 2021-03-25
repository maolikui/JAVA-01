package io.kimmking.rpcfx.config;

import io.kimmking.rpcfx.registry.ZooKeeperServiceAgent;
import io.kimmking.rpcfx.server.DefaultResolver;
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
@EnableConfigurationProperties({RpcfxProperties.class})
public class RpcfxServiceAutoConfiguration {
    @Autowired
    private RpcfxProperties rpcfxProperties;


    @Bean
    public ZooKeeperServiceAgent zooKeeperServiceAgent() {
        return new ZooKeeperServiceAgent(rpcfxProperties.getZkAddress());
    }

    @Bean
    public DefaultResolver defaultResolver(@Qualifier("zooKeeperServiceAgent") ZooKeeperServiceAgent zooKeeperServiceAgent) throws Exception {
        return new DefaultResolver(InetAddress.getLocalHost().getHostAddress() + ":" + rpcfxProperties.getPort(), zooKeeperServiceAgent);
    }
}
