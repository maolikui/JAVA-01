package com.liquid.filter;

import com.liquid.utils.GlobalSetting;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.Map;

/**
 * 添加请求头的Filter
 *
 * @author Liquid
 */
public class AddRequestHeaderFilter implements Filter {
    @Override
    public FullHttpResponse doFilter(FullHttpRequest fullHttpRequest, FilterChain chain) {
        Map<String, String> headers = GlobalSetting.getInstance().getMap("req-headers");
        headers.forEach((key, Value) -> {
            fullHttpRequest.headers().set(key, Value);
        });
        return chain.doFilter(fullHttpRequest);
    }
}
