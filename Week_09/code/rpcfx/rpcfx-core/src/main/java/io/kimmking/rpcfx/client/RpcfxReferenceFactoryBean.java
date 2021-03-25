package io.kimmking.rpcfx.client;


import io.kimmking.rpcfx.registry.ZooKeeperServiceAgent;
import org.springframework.beans.factory.FactoryBean;

/**
 * 客户端 stub Factory bean
 *
 * @param <T>
 * @author Liquid
 */
public class RpcfxReferenceFactoryBean<T> implements FactoryBean<T> {

    private Class<T> interfaceType;

    public RpcfxReferenceFactoryBean() {
    }

    public RpcfxReferenceFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() throws Exception {
        //目前只是支持注册一个client，去查询一次
        //可以通过注解设置，现在固定死
        ZooKeeperServiceAgent zooKeeperServiceAgent = new ZooKeeperServiceAgent("192.168.19.130:2181");
        String serverAddr = zooKeeperServiceAgent.discover(interfaceType.getName());
        return new RpcfxProxy(serverAddr).create(interfaceType);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
