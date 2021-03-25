package io.kimmking.rpcfx.client;

import cn.hutool.core.util.StrUtil;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.registry.ZooKeeperServiceAgent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用于创建 RPC 服务代理
 *
 * @author Liquid
 */
public class RpcfxProxy {

    private ZooKeeperServiceAgent serviceAgent;
    private String serviceAddress;

    public RpcfxProxy(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public RpcfxProxy(ZooKeeperServiceAgent serviceAgent) {
        this.serviceAgent = serviceAgent;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<?> interfaceClass) {
        // 创建动态代理对象
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RpcfxRequest request = new RpcfxRequest();
                        request.setServiceClass(method.getDeclaringClass().getName());
                        request.setMethod(method.getName());
                        request.setParameterTypes(method.getParameterTypes());
                        request.setParams(args);
                        // 获取 RPC 服务地址
                        if (serviceAgent != null) {
                            String serviceName = interfaceClass.getName();
                            serviceAddress = serviceAgent.discover(serviceName);
                        }
                        if (StrUtil.isEmpty(serviceAddress)) {
                            throw new RuntimeException("server address is empty");
                        }

                        // 从 RPC 服务地址中解析主机名与端口号
                        String[] array = StrUtil.split(serviceAddress, ":");
                        String host = array[0];
                        int port = Integer.parseInt(array[1]);
                        // 创建 RPC 客户端对象并发送 RPC 请求
                        RpcfxClient client = new RpcfxClient(host, port);
                        RpcfxResponse response = client.send(request);
                        if (response == null) {
                            throw new RuntimeException("response is null");
                        }
                        if (response.hasException()) {
                            throw response.getException();
                        } else {
                            return response.getResult();
                        }
                    }
                }
        );
    }
}
