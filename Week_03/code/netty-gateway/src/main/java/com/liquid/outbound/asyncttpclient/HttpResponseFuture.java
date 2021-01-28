package com.liquid.outbound.asyncttpclient;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class HttpResponseFuture extends AbstractResponseFuture<HttpResponse> {
    private static final HttpResponse TIMEOUT = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(520, "server timeout"));

    private volatile Object mAttachment;
    private volatile Channel mChannel;
    private volatile long mTouchTime;
    private final int mTimeout;
    final HttpRequest request; // package private

    public HttpResponseFuture(int timeout, HttpRequest request) {
        mTimeout = timeout;
        this.request = request;
        mTouchTime = currentTimeMillis();
    }

    public boolean isTimeout() {
        if (mTimeout + mTouchTime - currentTimeMillis() < 0) {
            return done(TIMEOUT);
        }
        return false;
    }

    public boolean done(HttpResponse result) {
        if (mChannel != null)
            mChannel.close();
        return super.done(result);
    }

    public void setAttachment(Object att) {
        mAttachment = att;
    }

    public Object getAttachment() {
        return mAttachment;
    }

    public boolean abort(Throwable t) {
        return done(TIMEOUT);
    }

    public HttpResponse get() throws InterruptedException, ExecutionException {
        try {
            return get(mTimeout, MILLISECONDS);
        } catch (TimeoutException e) {
            // ignore
            return TIMEOUT;
        }
    }

    public HttpResponse get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        long time = unit.toMillis(timeout);
        long wait = time + mTouchTime - currentTimeMillis();
        while (mLatch.getCount() > 0 && wait > 0) {
            mLatch.await(wait, MILLISECONDS);
            wait = time + mTouchTime - currentTimeMillis();
        }
        done(TIMEOUT);
        return mResult.get();
    }

    public void setChannel(Channel ch) {
        mChannel = ch;
    }

    public void touch() {
        mTouchTime = currentTimeMillis();
    }
}
