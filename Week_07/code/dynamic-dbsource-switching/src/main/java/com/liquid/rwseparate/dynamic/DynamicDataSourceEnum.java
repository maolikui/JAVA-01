package com.liquid.rwseparate.dynamic;

import lombok.Getter;

/**
 * 动态数据源名称枚举类
 *
 * @author Liquid
 */
@Getter
public enum DynamicDataSourceEnum {
    /**
     * 主库
     */
    PRIMARY("primary"),
    /**
     * 从库
     */
    SECONDARY("secondary");

    private String dataSourceName;

    DynamicDataSourceEnum(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }
}
