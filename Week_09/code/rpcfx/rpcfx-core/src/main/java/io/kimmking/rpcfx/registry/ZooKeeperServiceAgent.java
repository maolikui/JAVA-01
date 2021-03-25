package io.kimmking.rpcfx.registry;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 基于 ZooKeeper 的服务发现接口实现
 *
 * @author Liquid
 */
public class ZooKeeperServiceAgent {

    private final CuratorFramework zkClient;

    public ZooKeeperServiceAgent(String zkAddress) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        zkClient = CuratorFrameworkFactory.builder().connectString(zkAddress).namespace("rpcfx").retryPolicy(retryPolicy).build();
        zkClient.start();
    }

    public String discover(String serviceName) throws Exception {
        try {
            // 获取 service 节点
            String servicePath = "/" + serviceName;
            if (null == zkClient.checkExists().forPath(servicePath)) {
                throw new RuntimeException(String.format("can not find any service node on path: %s", servicePath));
            }

            List<String> addressList = new ArrayList<>();
            addressList = zkClient.getChildren().forPath(servicePath);

            if (CollectionUtil.isEmpty(addressList)) {
                throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
            }
            // 获取 address 节点
            String address;
            int size = addressList.size();
            if (size == 1) {
                // 若只有一个地址，则获取该地址
                address = addressList.get(0);
            } else {
                // 若存在多个地址，则随机获取一个地址
                address = addressList.get(ThreadLocalRandom.current().nextInt(size));
            }
            return address;
        } finally {
            zkClient.close();
        }
    }

    public void register(String serviceName, String serviceAddress) throws Exception {
        //根据serviceName创建持久节点
        try {
            if (null == zkClient.checkExists().forPath("/" + serviceName)) {
                zkClient.create().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName, "service".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //在服务名下, 根据address 创建临时节点
        zkClient.create().withMode(CreateMode.EPHEMERAL).
                forPath("/" + serviceName + "/" + serviceAddress, "provider".getBytes());
    }
}