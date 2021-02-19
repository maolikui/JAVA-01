package com.liquid.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义配置信息
 *
 * @author Liquid
 */
@ConfigurationProperties(prefix = "customer")
@Data
public class CustomerProperties {
    //用于配置班级的语文老师
    private String chineseTeacher;
}
