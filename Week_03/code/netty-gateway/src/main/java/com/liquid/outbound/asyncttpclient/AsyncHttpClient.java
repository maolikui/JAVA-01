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

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_0;

/**
 * 异步Http Client
 *
 * @author Liquid
 */
public class AsyncHttpClient {
    private final Bootstrap httpBootstrap;
    private final Queue<HttpResponseFuture> mFutures = new ConcurrentLinkedQueue<HttpResponseFuture>();

    public AsyncHttpClient() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        this.httpBootstrap = new Bootstrap();
        httpBootstrap.group(workerGroup);
        httpBootstrap.channel(NioSocketChannel.class);
        httpBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        httpBootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new HttpResponseDecoder());
                ch.pipeline().addLast(new HttpRequestEncoder());
                ch.pipeline().addLast(new HttpHandler());
            }
        });
    }

    public HttpResponseFuture execGet(String url, Map<String, Object> headers) {
        UriParser parser = new UriParser();
        parser.parse(null, url);
        HttpRequest request = buildRequest(GET, parser.path, headers, null);
        return execRequest(request, parser);
    }

    private HttpRequest buildRequest(HttpMethod method, String url, Map<String, Object> headers, Map<String, Object> params) {
        FullHttpRequest httpRequest = new DefaultFullHttpRequest(HTTP_1_0, method, url);
        httpRequest.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        // httpRequest.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        httpRequest.headers().add(HttpHeaderNames.CONTENT_LENGTH, httpRequest.content().readableBytes());
        return httpRequest;
    }

    private HttpResponseFuture execRequest(HttpRequest request, UriParser parser) {
        final HttpResponseFuture future = new HttpResponseFuture(60 * 1000 * 1000, request);
        try {
            ChannelFuture cf = httpBootstrap.connect(parser.host, parser.port);
            cf.addListener(new ConnectionListener(future));
            mFutures.add(future);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return future;
    }
}
