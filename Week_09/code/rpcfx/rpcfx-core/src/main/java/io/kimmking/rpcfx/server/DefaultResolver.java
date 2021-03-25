package io.kimmking.rpcfx.server;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.kimmking.rpcfx.annotation.RpcfxService;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.common.codec.RpcfxDecoder;
import io.kimmking.rpcfx.common.codec.RpcfxEncoder;
import io.kimmking.rpcfx.registry.ZooKeeperServiceAgent;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动装配使用的Resolver
 *
 * @author Liquid
 */
@Slf4j
public class DefaultResolver implements RpcfxResolver, ApplicationContextAware, InitializingBean {

    private String serviceAddress;

    private ZooKeeperServiceAgent serviceRegistry;
    /**
     * 针对集群的情况可以扩展成双重Map
     */
    private Map<String, Object> handlerMap = new HashMap<>();

    public DefaultResolver(String serviceAddress, ZooKeeperServiceAgent serviceRegistry) {
        this.serviceAddress = serviceAddress;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public Object resolve(String serviceClass) {
        return null;
    }

    /**
     * 获取RpcfxService注解的bean 缓存到自定义Map中
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcfxService.class);
        if (MapUtil.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                RpcfxService rpcfxService = serviceBean.getClass().getAnnotation(RpcfxService.class);
                String serviceName = rpcfxService.value().getName();
                //handlerMap缓存provider类
                handlerMap.put(serviceName, serviceBean);
            }
        }
    }

    /**
     * 启动Netty服务端
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (handlerMap.size() == 0) {
            //简化处理，当没有服务需要注册时不启动Netty客户端
            return;
        }
        new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup);
                bootstrap.channel(NioServerSocketChannel.class);
                bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new RpcfxDecoder(RpcfxRequest.class));
                        pipeline.addLast(new RpcfxEncoder(RpcfxResponse.class));
                        pipeline.addLast(new RpcfxServerHandler(handlerMap)); // 处理 RPC 请求
                    }
                });
                bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
                bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
                //解析配置启动的端口号
                String[] addressArray = StrUtil.split(serviceAddress, ":");
                String ip = addressArray[0];
                int port = Integer.parseInt(addressArray[1]);
                //绑定Netty服务端ip和端口号并启动
                ChannelFuture future = bootstrap.bind(ip, port).sync();
                //将缓存的服务信息注册到注册中心
                if (serviceRegistry != null) {
                    for (String interfaceName : handlerMap.keySet()) {
                        serviceRegistry.register(interfaceName, serviceAddress);
                    }
                }
                log.info("netty server started on port {}", port);
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                throw new RuntimeException("server started fail", e);
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }).start();
    }
}
