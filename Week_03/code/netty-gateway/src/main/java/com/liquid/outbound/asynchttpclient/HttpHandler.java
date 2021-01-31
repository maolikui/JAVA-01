package com.liquid.outbound.asynchttpclient;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Http Handler处理类
 *
 * @author Liquid
 */
public class HttpHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpResponseFuture future = (HttpResponseFuture) ctx.channel().attr(Constants.DEFAULT_ATTRIBUTE).get();
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String s = buf.toString(CharsetUtil.UTF_8);
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(s.getBytes("UTF-8")));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", fullHttpResponse.content().readableBytes());
            buf.release();
            ctx.channel().close();
            future.done(fullHttpResponse);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
        HttpResponseFuture future = (HttpResponseFuture) ctx.channel().attr(Constants.DEFAULT_ATTRIBUTE).get();
        if (future != null) {
            future.abort(cause);
        } else {
        }
    }
}