package com.liquid.rwseparate.dynamic;

/**
 * 使用ThreadLocal存储Key,通过DataSourceContext设置并存储
 *
 * @author Liquid
 */
public class DynamicDataSourceContext {
    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_CONTEXT = new ThreadLocal<>();

    public static void set(String dataSourceType) {
        DYNAMIC_DATASOURCE_CONTEXT.set(dataSourceType);
    }

    public static String get() {
        return DYNAMIC_DATASOURCE_CONTEXT.get();
    }

    public static void clear() {
        DYNAMIC_DATASOURCE_CONTEXT.remove();
    }

}
