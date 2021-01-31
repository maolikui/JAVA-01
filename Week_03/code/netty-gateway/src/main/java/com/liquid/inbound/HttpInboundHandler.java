package com.liquid.inbound;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.liquid.filter.Filter;
import com.liquid.filter.RealChain;
import com.liquid.outbound.asynchttpclient.NettyOutboundHandler;
import com.liquid.outbound.okhttp.OkhttpOutboundHandler;
import com.liquid.router.HttpEndpointRouter;
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
    private NettyOutboundHandler nettyOutboundHandler;
    private List<Filter> filterList;
    private HttpEndpointRouter httpEndpointRouter;


    public HttpInboundHandler(OkhttpOutboundHandler okhttpOutboundHandler, NettyOutboundHandler nettyOutboundHandler, List<Filter> filterList, HttpEndpointRouter httpEndpointRouter) {
        this.okhttpOutboundHandler = okhttpOutboundHandler;
        this.nettyOutboundHandler = nettyOutboundHandler;
        this.filterList = filterList;
        this.httpEndpointRouter = httpEndpointRouter;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // ================1.测试转发请求方法====================
        //  try {
        //     FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        //     String uri = fullHttpRequest.uri();
        //     if (uri.contains("/api/hello")) {
        //         handlerHello(fullHttpRequest, ctx);
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     ctx.close();
        // } finally {
        //     ReferenceCountUtil.release(msg);
        // }
        //================2.测试OkHttp client(过滤器,路由)转发请求方法============================
        // ExecutorUtils.getInstance().submit(() -> {
        //     try {
        //         FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        //         //使用过滤器链
        //         //异步执行
        //         RealChain realChain = new RealChain(ctx, okhttpOutboundHandler, filterList, 0);
        //         realChain.doFilter(fullHttpRequest);
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //         ctx.close();
        //     } finally {
        //         ReferenceCountUtil.release(msg);
        //     }
        //=================3.自定义AsyncHttpClient========================================
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            //使用过滤器链
            //异步执行
            RealChain realChain = new RealChain(ctx, null, nettyOutboundHandler, filterList, 0);
            realChain.doFilter(fullHttpRequest);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.close();
        } finally {
            ReferenceCountUtil.release(msg);
        }


        // try {
        //     FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        //     AsyncHttpClient httpClient = AsyncHttpClient.getInstance();
        //     Map<String, String> serviceMap = GlobalSetting.getInstance().getMap("service-name");
        //     String route = httpEndpointRouter.route(serviceMap.values().stream().map(this::formatUrl).collect(Collectors.toList()));
        //     HttpResponseFuture httpResponseFuture = httpClient.execGet(route + fullHttpRequest.uri(), null);
        //     httpResponseFuture.setExecutor(ExecutorUtils.getInstance().getExecutor());
        //     httpResponseFuture.addListener(new Runnable() {
        //         @Override
        //         public void run() {
        //             try {
        //                 FullHttpResponse fullHttpResponse = httpResponseFuture.get();
        //                 ctx.writeAndFlush(fullHttpResponse);
        //             } catch (InterruptedException e) {
        //                 e.printStackTrace();
        //             } catch (ExecutionException e) {
        //                 e.printStackTrace();
        //             }
        //         }
        //     });
        // } catch (Exception e) {
        //     ReferenceCountUtil.release(msg);
        // }
        //================4.使用开源Netty异步库=====================================
        // FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        // Map<String, String> serviceMap = GlobalSetting.getInstance().getMap("service-name");
        // String route = httpEndpointRouter.route(serviceMap.values().stream().map(this::formatUrl).collect(Collectors.toList()));
        // ListenableFuture<Response> whenResponse = NettyHttpClient2.getInstance().getAsyncHttpClient().prepareGet(route + fullHttpRequest.uri())
        //         .execute();
        // Runnable callback = () -> {
        //     try {
        //         Response response = whenResponse.get();
        //         FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(response.getResponseBody().getBytes("UTF-8")));
        //         fullHttpResponse.headers().set("Content-Type", "application/json");
        //         fullHttpResponse.headers().setInt("Content-Length", fullHttpResponse.content().readableBytes());
        //         if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
        //             ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
        //         } else {
        //             ctx.write(fullHttpResponse);
        //         }
        //         ctx.flush();
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     } catch (ExecutionException e) {
        //         e.printStackTrace();
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     } finally {
        //         ReferenceCountUtil.release(msg);
        //     }
        // };
        // whenResponse.addListener(callback, ExecutorUtils.getInstance().getExecutor());

        // NettyHttpClient.getInstance().submit("192.168.19.130", 8801, fullHttpRequest, new AtomicBoolean());
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
            String s = OkHttpUtils.getInstance().get("http://192.168.19.130:8801/api/hello", null);
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
            ctx.flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
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
