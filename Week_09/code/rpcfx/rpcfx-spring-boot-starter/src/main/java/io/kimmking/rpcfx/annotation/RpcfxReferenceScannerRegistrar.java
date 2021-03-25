package io.kimmking.rpcfx.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置扫描路径，将该路径下的接口动态代理注册到Spring容器里
 * (用于将引入的远程服务自动装配)
 *
 * @author Liquid
 */
public class RpcfxReferenceScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    /**
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(RpcfxReferenceScan.class.getName()));
        ClassPathRpcfxReferenceScanner scanner = new ClassPathRpcfxReferenceScanner(registry);
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }
        List<String> basePackages = new ArrayList<String>();
        for (String pkg : annoAttrs.getStringArray("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : annoAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        scanner.registerFilters();
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
