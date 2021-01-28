package com.liquid.outbound.asyncttpclient;


import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.URI;

public class ConnectionListener implements ChannelFutureListener {

    private HttpResponseFuture mFuture;
    private URI mUri;

    public ConnectionListener(HttpResponseFuture future, URI uri,
                              boolean isSocks) {
        mFuture = future;
        mUri = uri;
    }

    public void operationComplete(ChannelFuture f) throws Exception {
        Channel ch = f.channel();
        mFuture.setChannel(ch);
        ch.attr(Constants.DEFAULT_ATTRIBUTE).set(mFuture);
        if (f.isSuccess()) {
//            ch.pipeline().getContext(ResponseHandler.class)
//                    .setAttachment(mFuture);
            ch.writeAndFlush(mFuture.request).addListener(
                    new ChannelFutureListener() {
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
