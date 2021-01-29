package com.liquid.filter;

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
    public FullHttpResponse doFilter(FullHttpRequest fullHttpRequest) {
        if (index == filterList.size()) {
//            return okhttpOutboundHandler.handle(fullHttpRequest, ctx);
            return okhttpOutboundHandler.handlerDirect(fullHttpRequest, ctx);
        } else {
            //递归计数递增
            this.index++;
            Filter filter = filterList.get(index - 1);
            FullHttpResponse fullHttpResponse = filter.doFilter(fullHttpRequest, this);
            //递归回来计数递减
            this.index--;
            if (index == 0 && fullHttpRequest != null) {
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
