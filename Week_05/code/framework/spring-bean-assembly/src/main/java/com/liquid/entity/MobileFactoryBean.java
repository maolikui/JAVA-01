package com.liquid.entity;

import org.springframework.beans.factory.FactoryBean;

/**
 * Mobile Factory bean
 *
 * @author Liquid
 */
public class MobileFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new MobilePhone("iphone");
    }

    @Override
    public Class<?> getObjectType() {
        return MobilePhone.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
