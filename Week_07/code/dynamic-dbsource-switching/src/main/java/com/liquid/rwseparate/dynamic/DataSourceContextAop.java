package com.liquid.rwseparate.dynamic;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * AOP切面,拦截ReadOnly注解的方法执行
 *
 * @author Liquid
 */
@Slf4j
@Aspect
@Order(value = 1)
@Component
public class DataSourceContextAop {

    @Around("@annotation(com.liquid.rwseparate.dynamic.ReadOnly)")
    public Object setDynamicDataSource(ProceedingJoinPoint pjp) throws Throwable {
        boolean clear = true;
        try {
            Method method = this.getMethod(pjp);
            if (method.isAnnotationPresent(ReadOnly.class)) {
                DynamicDataSourceContext.set(DynamicDataSourceEnum.SECONDARY.getDataSourceName());
            } else {
                DynamicDataSourceContext.set(DynamicDataSourceEnum.PRIMARY.getDataSourceName());
            }
            log.info("========数据源切换至：{}", DynamicDataSourceContext.get());
            return pjp.proceed();
        } finally {
            if (clear) {
                DynamicDataSourceContext.clear();
            }

        }
    }

    private Method getMethod(JoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }

}
