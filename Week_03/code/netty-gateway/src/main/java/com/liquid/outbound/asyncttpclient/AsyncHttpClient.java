package com.liquid.outbound.asyncttpclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;

import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 异步Http Client
 *
 * @author Liquid
 */
public class AsyncHttpClient {
    private final Bootstrap httpBootstrap;
    private final Queue<HttpResponseFuture> mFutures = new ConcurrentLinkedQueue<HttpResponseFuture>();
    private static final AttributeKey<Object> DEFAULT_ATTRIBUTE = AttributeKey.valueOf("default");

    public AsyncHttpClient() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        this.httpBootstrap = new Bootstrap();
        httpBootstrap.group(workerGroup);
        httpBootstrap.channel(NioSocketChannel.class);
        httpBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        httpBootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                ch.pipeline().addLast(new HttpResponseDecoder());
//                客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                ch.pipeline().addLast(new HttpRequestEncoder());
                ch.pipeline().addLast(new HttpHandler());
            }
        });
    }

    public HttpResponseFuture execGet(URI uri, Map<String, Object> headers, Proxy proxy) {
        HttpRequest request = buildRequest(GET, uri, headers, null, proxy);
        return execRequest(request, uri, proxy);
    }

    private HttpRequest buildRequest(HttpMethod method, URI uri,
                                     Map<String, Object> headers, Map<String, Object> params,
                                     Proxy proxy) {
        HttpRequest request = new DefaultHttpRequest(HTTP_1_1, method, uri.toString());
        return request;
    }

    private HttpResponseFuture execRequest(HttpRequest request, URI uri,
                                           Proxy proxy)  {
        if (proxy == null) {
            proxy = Proxy.NO_PROXY;
        }
        URI uriTemp = null;
        try {
            uriTemp = new URI("/api/hello");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        FullHttpRequest requestTemp = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uriTemp.toASCIIString());
        requestTemp.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        requestTemp.headers().add(HttpHeaderNames.CONTENT_LENGTH, requestTemp.content().readableBytes());

        final HttpResponseFuture future = new HttpResponseFuture(60, requestTemp);
        try {
            ChannelFuture cf = httpBootstrap.connect("127.0.0.1", 8088);
            cf.addListener(new ConnectionListener(future, uri,
                    false));
            mFutures.add(future);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return future;
    }
}
