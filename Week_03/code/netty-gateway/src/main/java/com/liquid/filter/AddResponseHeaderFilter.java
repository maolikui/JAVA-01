package com.liquid.filter;

import cn.hutool.core.util.ObjectUtil;
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
    /**
     * 用于链式调用
     *
     * @param fullHttpRequest
     * @param chain
     * @return
     */
    @Override
    public FullHttpResponse doFilter(FullHttpRequest fullHttpRequest, FilterChain chain) {
        FullHttpResponse fullHttpResponse = chain.doFilter(fullHttpRequest);
        if (ObjectUtil.isNotEmpty(fullHttpResponse)) {
            Map<String, String> headers = GlobalSetting.getInstance().getMap("res-headers");
            headers.forEach((key, Value) -> {
                fullHttpResponse.headers().set(key, Value);
            });
        }
        return fullHttpResponse;
    }

    /**
     * 用于异步处理响应Response
     *
     * @param fullHttpResponse
     * @return
     */
    public void filter(FullHttpResponse fullHttpResponse) {
        Map<String, String> headers = GlobalSetting.getInstance().getMap("res-headers");
        headers.forEach((key, Value) -> {
            fullHttpResponse.headers().set(key, Value);
        });
    }
}
