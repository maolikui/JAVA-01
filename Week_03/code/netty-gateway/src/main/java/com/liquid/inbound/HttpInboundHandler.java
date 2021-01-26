package com.liquid.inbound;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.liquid.filter.Filter;
import com.liquid.filter.RealChain;
import com.liquid.outbound.okhttp.OkhttpOutboundHandler;
import com.liquid.utils.OkHttpUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

import java.util.List;


/**
 * Http请求入站处理器
 *
 * @author Liquid
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Log log = LogFactory.get();
    private OkhttpOutboundHandler okhttpOutboundHandler;
    private List<Filter> filterList;

    public HttpInboundHandler(OkhttpOutboundHandler okhttpOutboundHandler, List<Filter> filterList) {
        this.okhttpOutboundHandler = okhttpOutboundHandler;
        this.filterList = filterList;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //测试转发请求方法
//            String uri = fullRequest.uri();
//            if (uri.contains("/api/hello")) {
//                  handlerHello(fullRequest, ctx);
//            }
//            okhttpOutboundHandler.handle(fullRequest, ctx);
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            RealChain realChain = new RealChain(ctx, okhttpOutboundHandler, filterList, 0);
            DefaultFullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
            realChain.doFilter(fullHttpRequest, fullHttpResponse);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 整合了OkHttp转发请求至给定Server,url为:http://localhost:8088/api/hello
     *
     * @param fullRequest
     * @param ctx
     */
    private void handlerHello(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        FullHttpResponse response = null;
        try {
            String s = OkHttpUtils.getInstance().get("http://localhost:8088/api/hello", null);
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(s.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
        } catch (Exception e) {
            log.error("处理测试接口出错", e);
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set("keep-alive", true);
                    ctx.write(response);
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
