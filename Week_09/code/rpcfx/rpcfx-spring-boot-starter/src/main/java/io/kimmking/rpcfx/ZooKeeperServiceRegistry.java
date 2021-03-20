package io.kimmking.rpcfx;

import io.kimmking.rpcfx.api.ServiceRegistry;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于 ZooKeeper 的服务注册接口实现
 *
 * @author Liquid
 */
public class ZooKeeperServiceRegistry implements ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceRegistry.class);

    private final CuratorFramework zkClient;

    public ZooKeeperServiceRegistry(String zkAddress) {
        // 创建 ZooKeeper 客户端
        // start zk client
        //TO-DO 此处可以使用配置文件进行配置
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        zkClient = CuratorFrameworkFactory.builder().connectString(zkAddress).namespace("rpcfx").retryPolicy(retryPolicy).build();
        zkClient.start();
    }

    @Override
    public void register(String serviceName, String serviceAddress) throws Exception {
        //根据serviceName创建持久节点
        try {
            if (null == zkClient.checkExists().forPath("/" + serviceName)) {
                zkClient.create().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName, "service".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //根据address 创建临时节点
        zkClient.create().withMode(CreateMode.EPHEMERAL).
                forPath("/" + serviceAddress, "provider".getBytes());
    }
}