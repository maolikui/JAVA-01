package com.liquid.outbound.netty4;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Netty Http Client
 *
 * @author Liquid
 */
public class NettyHttpClient {
    private static Log log = LogFactory.get();

    private Bootstrap bootstrap;
    private NioEventLoopGroup group = new NioEventLoopGroup();

    private NettyHttpClient() {
        bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                              @Override
                              protected void initChannel(SocketChannel ch) throws Exception {
                                  //客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                                  ch.pipeline().addLast(new HttpResponseDecoder());
                                  //客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                                  ch.pipeline().addLast(new HttpRequestEncoder());
                                  ch.pipeline().addLast(new NettyHttpClientOutboundHandler());
                              }
                          }
        );
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.option(ChannelOption.SO_REUSEADDR, false);
    }

    private static class NettyHttpClientHolder {
        private static NettyHttpClient INSTANCE = new NettyHttpClient();
    }

    public static NettyHttpClient getInstance() {
        return NettyHttpClientHolder.INSTANCE;
    }

    public void submit(String host, Integer port, HttpRequest request, final AtomicBoolean cancelled) {
        ChannelFuture channelFuture = bootstrap.connect(host, port);


        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    Throwable cause = future.cause();
                    //可以处理cause
                    cancelled.set(true);
                }
                if (cancelled.get()) {
                    future.cancel(true);
                    if (future.channel().isOpen()) {
                        future.channel().close();
                    }
                    return;
                }


//                Channel channel = future.channel();
//                ChannelPromise promise = channel.newPromise();
//                ChannelFuture f = channel.writeAndFlush(request, promise);
//                Void aVoid = f.get();
//                f.addListener(new WriteCompleteListener(future));
            }
        });
    }
}
