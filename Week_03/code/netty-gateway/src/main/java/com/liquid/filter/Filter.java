package com.liquid.filter;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Filter接口类,使用简单责任链模式优化，具备扩展性
 *
 * @author Liquid
 */
public interface Filter {
    FullHttpResponse doFilter(FullHttpRequest fullHttpRequest, FilterChain chain);
}
