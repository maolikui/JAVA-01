package com.liquid.outbound.asynchttpclient;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 自定义Http Response Future
 *
 * @author Liquid
 */
public class HttpResponseFuture extends AbstractResponseFuture<FullHttpResponse> {
    private long timeout;
    private volatile Channel mChannel;
    private volatile long mTouchTime;
    final HttpRequest request;

    public HttpResponseFuture(int timeout, HttpRequest request) {
        this.request = request;
        this.timeout = timeout;
        mTouchTime = currentTimeMillis();
    }

    @Override
    public boolean done(FullHttpResponse result) {
        if (mChannel != null)
            mChannel.close();
        return super.done(result);
    }

    @Override
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public boolean abort(Throwable t) {
        return done(Constants.TIMEOUT);
    }

    @Override
    public FullHttpResponse get() throws InterruptedException, ExecutionException {
        try {
            return get(timeout, MILLISECONDS);
        } catch (TimeoutException e) {
            return Constants.TIMEOUT;
        }
    }

    @Override
    public FullHttpResponse get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        long time = unit.toMillis(timeout);
        long wait = time + mTouchTime - currentTimeMillis();
        if (mLatch.getCount() < 1 && wait > 0) {
            return mResult.get();
        }
        //请求线程没有处理完也没有超时
        if (mLatch.getCount() > 0 && wait > 0) {
            mLatch.await(wait, MILLISECONDS);
            wait = time + mTouchTime - currentTimeMillis();
            if (wait > 0) {
                return mResult.get();
            }
        }
        done(Constants.TIMEOUT);
        return mResult.get();
    }

    public void setChannel(Channel ch) {
        mChannel = ch;
    }

    @Override
    public void touch() {
        mTouchTime = currentTimeMillis();
    }
}
