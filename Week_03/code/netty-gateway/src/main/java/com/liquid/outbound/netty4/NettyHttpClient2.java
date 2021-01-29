package com.liquid.outbound.netty4;

import org.asynchttpclient.AsyncHttpClient;

import static org.asynchttpclient.Dsl.asyncHttpClient;

public class NettyHttpClient2 {
    private AsyncHttpClient client;

    private NettyHttpClient2() {
        client = asyncHttpClient();
    }

    private static class NettyHttpClient2Holder {
        private static NettyHttpClient2 INSTANCE = new NettyHttpClient2();
    }

    public static NettyHttpClient2 getInstance() {
        return NettyHttpClient2Holder.INSTANCE;
    }

    public AsyncHttpClient getAsyncHttpClient() {
        return client;
    }
}
