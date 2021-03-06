package com.liquid.rwseparate.dynamic;

import java.lang.annotation.*;

/**
 * ReadOnly注解
 *
 * @author Liquid
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ReadOnly {
    DynamicDataSourceEnum value() default DynamicDataSourceEnum.SECONDARY;
}
