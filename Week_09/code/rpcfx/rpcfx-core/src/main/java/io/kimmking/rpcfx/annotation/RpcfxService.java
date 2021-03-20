package io.kimmking.rpcfx.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rpcfx Service注解类
 *
 * @author Liquid
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcfxService {
    /**
     * 服务接口类
     */
    Class<?> value();
}
