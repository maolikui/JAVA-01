package com.liquid.filter;

import cn.hutool.core.util.ObjectUtil;
import com.liquid.outbound.asynchttpclient.NettyOutboundHandler;
import com.liquid.outbound.okhttp.OkhttpOutboundHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import java.util.List;

/**
 * FilterChain实现类
 *
 * @author Liquid
 */
public class RealChain implements FilterChain {
    private OkhttpOutboundHandler okhttpOutboundHandler;
    private NettyOutboundHandler nettyOutboundHandler;
    private ChannelHandlerContext ctx;
    private List<Filter> filterList;
    //当前已经处理过的Filter数量
    private int index = 0;

    public RealChain(ChannelHandlerContext ctx, OkhttpOutboundHandler okhttpOutboundHandler, NettyOutboundHandler nettyOutboundHandler, List<Filter> filterList, int index) {
        this.okhttpOutboundHandler = okhttpOutboundHandler;
        this.nettyOutboundHandler = nettyOutboundHandler;
        this.ctx = ctx;
        this.filterList = filterList;
        this.index = index;
    }

    @Override
    public FullHttpResponse doFilter(FullHttpRequest fullHttpRequest) {
        if (index == filterList.size()) {
            //用于压测网关直接返回客户端
            // return okhttpOutboundHandler.handlerDirect(fullHttpRequest, ctx);
            //用于压测网关通过OkHttpClient转发
            if (ObjectUtil.isNotEmpty(okhttpOutboundHandler)) {
                return okhttpOutboundHandler.handle(fullHttpRequest, ctx);
            }
            //用于压测网关通过异步http client转发
            if (ObjectUtil.isNotEmpty(nettyOutboundHandler)) {
                return nettyOutboundHandler.handle(fullHttpRequest, ctx);
            }
            return null;
        } else {
            //递归计数递增
            this.index++;
            Filter filter = filterList.get(index - 1);
            FullHttpResponse fullHttpResponse = filter.doFilter(fullHttpRequest, this);
            //递归回来计数递减
            this.index--;
            //当fullHttpResponse返回为null时，说面是异步请求后的情况，直接返回，避免阻塞网关Server请求处理线程
            if (ObjectUtil.isNotEmpty(fullHttpResponse) && index == 0 && fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(fullHttpResponse);
                }
                ctx.flush();
            }
            return fullHttpResponse;
        }
    }
}
