package com.liquid.inbound;

import com.liquid.filter.AddRequestHeaderFilter;
import com.liquid.filter.AddResponseHeaderFilter;
import com.liquid.filter.Filter;
import com.liquid.outbound.okhttp.OkhttpOutboundHandler;
import com.liquid.router.RoundRobinRouter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义ChannelInitializer用于向Pipeline添加Http协议请求处理和自定义相关Handler
 *
 * @author Liquid
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        //设置轮询算法负载均衡
        RoundRobinRouter roundRobinRouter = new RoundRobinRouter();
        //添加过滤器
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new AddRequestHeaderFilter());
        filterList.add(new AddResponseHeaderFilter());
        p.addLast(new HttpInboundHandler(new OkhttpOutboundHandler(roundRobinRouter), filterList));
    }
}
