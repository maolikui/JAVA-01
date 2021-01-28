package com.liquid.outbound.asyncttpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractResponseFuture<V> implements ResponseFuture<V> {
    protected Runnable mFirstListener;
    protected final CountDownLatch mLatch = new CountDownLatch(1);
    protected final AtomicReference<V> mResult = new AtomicReference<V>();

    protected List<Runnable> mOtherListeners;

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
            l.run();// only called once
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return mResult.get() != null;
    }

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
}
