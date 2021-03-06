package com.liquid.rwseparate.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * AbstractRoutingDataSource实现类
 *
 * @author Liquid
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 模板方法
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContext.get();
    }

}
