package com.liquid.filter;

import com.liquid.utils.GlobalSetting;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.Map;

/**
 * 给响应添加Header的Filter
 *
 * @author Liquid
 */
public class AddResponseHeaderFilter implements Filter {
    @Override
    public FullHttpResponse doFilter(FullHttpRequest fullHttpRequest, FilterChain chain) {
        FullHttpResponse fullHttpResponse = chain.doFilter(fullHttpRequest);
        Map<String, String> headers = GlobalSetting.getInstance().getMap("res-headers");
        headers.forEach((key, Value) -> {
            fullHttpResponse.headers().set(key, Value);
        });
        return fullHttpResponse;
    }
}
