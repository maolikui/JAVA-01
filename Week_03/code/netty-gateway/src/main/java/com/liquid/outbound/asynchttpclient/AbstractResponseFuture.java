package com.liquid.outbound.asynchttpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自定义响应Future抽象类
 *
 * @param <V>
 * @author Liquid
 */
public abstract class AbstractResponseFuture<V> implements ResponseFuture<V> {
    protected Runnable mFirstListener;
    protected final CountDownLatch mLatch = new CountDownLatch(1);
    protected final AtomicReference<V> mResult = new AtomicReference<V>();
    protected List<Runnable> mOtherListeners;
    protected Executor executor;

    @Override
    public synchronized void addListener(Runnable listener) {
        if (mLatch.getCount() == 0) {
            notifyListener(listener);
        } else {
            if (mFirstListener == null) {
                mFirstListener = listener;
            } else {
                if (mOtherListeners == null) {
                    mOtherListeners = new ArrayList<Runnable>(1);
                }
                mOtherListeners.add(listener);
            }
        }
    }

    protected void notifyListener(Runnable l) {
        try {
            // only called once
            executor.execute(l);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return mResult.get() != null;
    }

    @Override
    public synchronized boolean done(V result) {
        if (mResult.compareAndSet(null, result)) {
            mLatch.countDown();
            if (mFirstListener != null) {
                notifyListener(mFirstListener);
                mFirstListener = null;
                if (mOtherListeners != null) {
                    for (Runnable r : mOtherListeners) {
                        notifyListener(r);
                    }
                    mOtherListeners = null;
                }
            }
            return true;
        }
        return false;
    }

    public abstract void setExecutor(Executor executor);
}
