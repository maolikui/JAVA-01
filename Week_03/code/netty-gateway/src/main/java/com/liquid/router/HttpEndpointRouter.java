package com.liquid.router;

import java.util.List;

/**
 * Router接口类
 *
 * @author Liquid
 */
public interface HttpEndpointRouter {
    String route(List<String> endpoints);
}
