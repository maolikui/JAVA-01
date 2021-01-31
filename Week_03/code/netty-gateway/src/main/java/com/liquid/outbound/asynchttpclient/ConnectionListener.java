package com.liquid.outbound.asynchttpclient;


import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * 连接监听器
 *
 * @author Liquid
 */
public class ConnectionListener implements ChannelFutureListener {

    private HttpResponseFuture mFuture;

    public ConnectionListener(HttpResponseFuture future) {
        mFuture = future;
    }

    @Override
    public void operationComplete(ChannelFuture f) throws Exception {
        Channel ch = f.channel();
        mFuture.setChannel(ch);
        ch.attr(Constants.DEFAULT_ATTRIBUTE).set(mFuture);
        if (f.isSuccess()) {
            //连接成功后发送请求
            ch.writeAndFlush(mFuture.request).addListener(
                    new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future)
                                throws Exception {
                            mFuture.touch();
                        }
                    });
        } else {
            ch.close();
            Throwable cause = f.cause();
            mFuture.abort(cause);
        }
    }
}
