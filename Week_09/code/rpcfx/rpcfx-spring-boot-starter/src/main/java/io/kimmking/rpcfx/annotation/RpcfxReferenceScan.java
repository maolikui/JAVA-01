package io.kimmking.rpcfx.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义扫描包注解
 *
 * @author Liquid
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RpcfxReferenceScannerRegistrar.class)
public @interface RpcfxReferenceScan {

    String[] value() default {};

    String[] basePackages() default {};
}
