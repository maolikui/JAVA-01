package com.liquid.filter;

import cn.hutool.setting.Setting;
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
    public void doFilter(FullHttpRequest fullHttpRequest, FullHttpResponse fullHttpResponse, FilterChain chain) {
        Setting setting = new Setting("service_config.setting");
        Map<String, String> headers = setting.getMap("req-headers");
        headers.forEach((key, Value) -> {
            fullHttpRequest.headers().set(key, Value);
        });
        chain.doFilter(fullHttpRequest, fullHttpResponse);
    }
}
