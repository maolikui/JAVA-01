package io.kimmking.rpcfx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ZK配置
 *
 * @author Liquid
 */
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
