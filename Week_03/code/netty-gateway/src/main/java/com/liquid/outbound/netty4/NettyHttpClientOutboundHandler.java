package com.liquid.outbound.netty4;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;

/**
 * Netty 处理
 *
 * @author Liquid
 */
public class NettyHttpClientOutboundHandler extends ChannelInboundHandlerAdapter {
    private static Log log = LogFactory.get();

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        log.info("NettyHttpClientOutboundHandler channelActive方法执行");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        log.info("NettyHttpClientOutboundHandler channelRead方法执行");
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            String res = content.toString(CharsetUtil.UTF_8);
            log.info("res: " + res);
        }
    }
}