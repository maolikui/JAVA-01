package com.liquid.rwseparate.dynamic;

import java.util.List;

/**
 * Router接口类
 *
 * @author Liquid
 */
public interface DataSourceRouter {
    String route(List<String> dsIds);
}
