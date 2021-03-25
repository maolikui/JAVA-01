package io.kimmking.rpcfx.client;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.common.codec.RpcfxDecoder;
import io.kimmking.rpcfx.common.codec.RpcfxEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * RPC 客户端（用于发送 RPC 请求）
 *
 * @author Liquid
 */
public class RpcfxClient extends SimpleChannelInboundHandler<RpcfxResponse> {

    private final String host;
    private final int port;

    private RpcfxResponse response;

    public RpcfxClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, RpcfxResponse response) throws Exception {
        this.response = response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public RpcfxResponse send(RpcfxRequest request) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new RpcfxEncoder(RpcfxRequest.class)); // 编码 RPC 请求
                    pipeline.addLast(new RpcfxDecoder(RpcfxResponse.class)); // 解码 RPC 响应
                    pipeline.addLast(RpcfxClient.this); // 处理 RPC 响应
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            ChannelFuture future = bootstrap.connect(host, port).sync();
            Channel channel = future.channel();
            channel.writeAndFlush(request).sync();
            channel.closeFuture().sync();
            return response;
        } finally {
            group.shutdownGracefully();
        }
    }
}
