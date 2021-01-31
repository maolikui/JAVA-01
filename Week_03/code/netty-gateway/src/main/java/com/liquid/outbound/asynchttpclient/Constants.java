package com.liquid.outbound.asynchttpclient;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AttributeKey;

/**
 * 常量类
 *
 * @author Liquid
 */
public interface Constants {
    AttributeKey<Object> DEFAULT_ATTRIBUTE = AttributeKey.valueOf("default");
    FullHttpResponse TIMEOUT = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(520, "server timeout"));
}
