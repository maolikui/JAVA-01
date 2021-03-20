package io.kimmking.rpcfx;

import io.kimmking.rpcfx.api.ServiceDiscovery;


/**
 * 基于 ZooKeeper 的服务发现接口实现
 *
 * @author Liquid
 */
public class ZooKeeperServiceDiscovery implements ServiceDiscovery {
    private String zkAddress;

    public ZooKeeperServiceDiscovery(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    @Override
    public String discover(String name) {
        return null;
    }
}