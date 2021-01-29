package com.liquid.outbound.okhttp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.liquid.router.HttpEndpointRouter;
import com.liquid.utils.GlobalSetting;
import com.liquid.utils.OkHttpUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OkHttp 处理Handler类
 *
 * @author Liquid
 */
public class OkhttpOutboundHandler {
    private static Log log = LogFactory.get();
    private HttpEndpointRouter httpEndpointRouter;

    public OkhttpOutboundHandler(HttpEndpointRouter httpEndpointRouter) {
        this.httpEndpointRouter = httpEndpointRouter;
    }

    public FullHttpResponse handle(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        FullHttpResponse fullHttpResponse = null;
        //负载均衡处理
        //模拟根据微服务名称获取服务列表
        Map<String, String> serviceMap = GlobalSetting.getInstance().getMap("service-name");
        String route = httpEndpointRouter.route(serviceMap.values().stream().map(this::formatUrl).collect(Collectors.toList()));
        String url = route + fullHttpRequest.uri();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("mao", fullHttpRequest.headers().get("mao"));
        try {
            String res = OkHttpUtils.getInstance().get(url, null, headers);
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", fullHttpResponse.content().readableBytes());
        } catch (Exception e) {
            log.error("处理测试接口出错", e);
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        }
        return fullHttpResponse;
    }

    /**
     * 用于压测处理直接返回给客户端请求的情况
     *
     * @param fullRequest
     * @param ctx
     * @return
     */
    public FullHttpResponse handlerDirect(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        FullHttpResponse fullHttpResponse = null;
        try {
            String value = "hello,kimmking";
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", fullHttpResponse.content().readableBytes());
        } catch (Exception e) {
            System.out.println("处理出错:" + e.getMessage());
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        }
        return fullHttpResponse;
    }

    /**
     * 格式化url字符串
     *
     * @param url
     * @return
     */
    private String formatUrl(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

}
