package io.kimmking.rpcfx.server;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.common.exception.RpcfxException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.util.Map;

/**
 * RPC 服务端处理器
 *
 * @author Liquid
 */
public class RpcfxServerHandler extends SimpleChannelInboundHandler<RpcfxRequest> {

    private final Map<String, Object> handlerMap;

    public RpcfxServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, RpcfxRequest request) throws Exception {
        RpcfxResponse response = new RpcfxResponse();
        try {
            Object result = handle(request);
            response.setResult(result);
        } catch (Exception e) {
            response.setException(new RpcfxException(e.getMessage(), e.getCause()));
        }
        // 写入 RPC 响应对象并自动关闭连接
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handle(RpcfxRequest request) throws Exception {
        // 从请求体中获取要调用的服务名称
        String serviceName = request.getServiceClass();
        //从缓存的服务Map中获取服务实例
        Object serviceBean = handlerMap.get(serviceName);
        if (serviceBean == null) {
            throw new RuntimeException(String.format("can not find service bean by key: %s", serviceName));
        }
        // 获取反射调用所需的参数
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethod();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParams();
        //此处可以直接反射调用
        //也可以使用 CGLib 执行反射调用
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
