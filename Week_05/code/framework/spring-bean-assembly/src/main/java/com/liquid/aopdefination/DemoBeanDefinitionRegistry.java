package com.liquid.aopdefination;

import com.liquid.aopdefination.service.DemoService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 用于动态注入自定义的接口
 *
 * @author Liquid
 */
@Component
public class DemoBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DemoService.class);
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(DemoService.class);
        beanDefinition.setBeanClass(ServiceFactory.class);
        beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        registry.registerBeanDefinition(DemoService.class.getSimpleName(), beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
