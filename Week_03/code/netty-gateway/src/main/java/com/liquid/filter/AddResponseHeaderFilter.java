package com.liquid.filter;

import cn.hutool.setting.Setting;
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
    public void doFilter(FullHttpRequest fullHttpRequest, FullHttpResponse fullHttpResponse, FilterChain chain) {
        chain.doFilter(fullHttpRequest, fullHttpResponse);
        Setting setting = new Setting("service_config.setting");
        Map<String, String> headers = setting.getMap("res-headers");
        headers.forEach((key, Value) -> {
            fullHttpResponse.headers().set(key, Value);
        });
    }
}
