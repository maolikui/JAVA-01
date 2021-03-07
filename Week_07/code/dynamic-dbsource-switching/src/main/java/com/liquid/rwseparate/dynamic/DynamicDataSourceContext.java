package com.liquid.rwseparate.dynamic;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用ThreadLocal存储Key,通过DataSourceContext设置并存储
 *
 * @author Liquid
 */
public class DynamicDataSourceContext {
    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_CONTEXT = new ThreadLocal<>();
    public static List<String> primaryDsIds = new ArrayList<>();
    public static List<String> secondaryDsIds = new ArrayList<>();
    public static Map<String, DataSourceRouter> dsRouters = new HashMap<>();
    public static DataSourceRouter routerChoosed;

    //初始化负载均衡算法
    //负载均衡算法怎样扩展?
    static {
        dsRouters.put("round-robin", new RoundRobinRouter());
        dsRouters.put("random", new RandomRouter());
    }

    public static void set(String dataSourceType) {
        DYNAMIC_DATASOURCE_CONTEXT.set(dataSourceType);
    }

    public static String get() {
        return DYNAMIC_DATASOURCE_CONTEXT.get();
    }

    public static void clear() {
        DYNAMIC_DATASOURCE_CONTEXT.remove();
    }

    public static void chooseRouter(String lb) {
        if (StrUtil.isEmpty(lb)) {
            routerChoosed = dsRouters.get("round-robin");
            return;
        }
        routerChoosed = dsRouters.get(lb);
    }
}
