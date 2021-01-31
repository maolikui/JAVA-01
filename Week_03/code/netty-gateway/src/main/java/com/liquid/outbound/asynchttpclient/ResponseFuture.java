package com.liquid.outbound.asynchttpclient;

import java.util.concurrent.Future;

/**
 * 自定义响应Future
 *
 * @param <V>
 * @author Liquid
 */
public interface ResponseFuture<V> extends Future<V> {
    boolean done(V result);

    void touch();

    boolean abort(Throwable t);

    void addListener(Runnable listener);
}
