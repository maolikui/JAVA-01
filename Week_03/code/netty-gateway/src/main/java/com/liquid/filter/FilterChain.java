package com.liquid.filter;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * 过滤器链
 *
 * @author Liquid
 */
public interface FilterChain {
    FullHttpResponse doFilter(FullHttpRequest fullHttpRequest);
}
