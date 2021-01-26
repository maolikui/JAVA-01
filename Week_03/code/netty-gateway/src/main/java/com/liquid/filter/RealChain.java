package com.liquid.filter;

import com.liquid.outbound.okhttp.OkhttpOutboundHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import java.util.List;

/**
 * FilterChain真正实现类
 *
 * @author Liquid
 */
public class RealChain implements FilterChain {
    private OkhttpOutboundHandler okhttpOutboundHandler;
    private ChannelHandlerContext ctx;
    private List<Filter> filterList;
    //当前已经处理过的Filter数量
    private int index = 0;

    public RealChain(ChannelHandlerContext ctx, OkhttpOutboundHandler okhttpOutboundHandler, List<Filter> filterList, int index) {
        this.okhttpOutboundHandler = okhttpOutboundHandler;
        this.ctx = ctx;
        this.filterList = filterList;
        this.index = index;
    }

    @Override
    public void doFilter(FullHttpRequest fullHttpRequest, FullHttpResponse fullHttpResponse) {
        if (index == filterList.size()) {
            okhttpOutboundHandler.handle(fullHttpRequest, fullHttpResponse, ctx);
        } else {
            this.index++;
            Filter filter = filterList.get(index - 1);
            filter.doFilter(fullHttpRequest, fullHttpResponse, this);
            if (index == 1 && fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    fullHttpResponse.headers().set("keep-alive", true);
                    ctx.write(fullHttpResponse);
                }
            }
        }

    }
}
