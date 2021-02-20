package com.liquid.entity;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ImportBeanDefinitionRegistrar Demo
 *
 * @author Liquid
 */
public class ImportBeanDefinitionRegistrarDemo implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        RootBeanDefinition definition = new RootBeanDefinition(ImportDemo3.class);
        beanDefinitionRegistry.registerBeanDefinition("importDemo3", definition);
    }
}
