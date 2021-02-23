package com.liquid.aopdefination;

import cn.hutool.json.JSONUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 *
 * @param <T>
 * @author Liquid
 */
public class ServiceProxy<T> implements InvocationHandler {
    private Class<T> interfaceType;

    public ServiceProxy(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //过滤掉继承自Object类中的方法
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        System.out.println("参数: " + args);
        System.out.println("结果: " + JSONUtil.toJsonStr(args));
        return null;
    }
}
