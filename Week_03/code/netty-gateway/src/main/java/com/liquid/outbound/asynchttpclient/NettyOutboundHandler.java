package com.liquid.outbound.asynchttpclient;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.liquid.filter.Filter;
import com.liquid.router.HttpEndpointRouter;
import com.liquid.utils.ExecutorUtils;
import com.liquid.utils.GlobalSetting;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Netty处理Handler
 *
 * @author Liquid
 */
public class NettyOutboundHandler {
    private static Log log = LogFactory.get();
    private HttpEndpointRouter httpEndpointRouter;
    private List<Filter> responseFilters;

    public NettyOutboundHandler(HttpEndpointRouter httpEndpointRouter, List<Filter> responseFilters) {
        this.httpEndpointRouter = httpEndpointRouter;
        this.responseFilters = responseFilters;
    }

    public FullHttpResponse handle(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        Map<String, String> serviceMap = GlobalSetting.getInstance().getMap("service-name");
        String route = httpEndpointRouter.route(serviceMap.values().stream().map(this::formatUrl).collect(Collectors.toList()));
        HttpResponseFuture httpResponseFuture = AsyncHttpClient.getInstance().execGet(route + fullHttpRequest.uri(), null);
        httpResponseFuture.setExecutor(ExecutorUtils.getInstance().getExecutor());
        httpResponseFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    FullHttpResponse fullHttpResponse = httpResponseFuture.get();
                    //单独异步处理Response Header
                    responseFilters.stream().forEach((responseFilter) -> {
                        responseFilter.filter(fullHttpResponse);
                    });
                    ctx.writeAndFlush(fullHttpResponse);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        return null;
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
