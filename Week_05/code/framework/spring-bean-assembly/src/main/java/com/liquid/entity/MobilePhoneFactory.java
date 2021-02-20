package com.liquid.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * MobilePhone Factory
 *
 * @author Liquid
 */
public class MobilePhoneFactory {
    private static Map<String, MobilePhone> mobilePhones = new HashMap<String, MobilePhone>();

    static {
        mobilePhones.put("huawei", new MobilePhone("huawei"));
        mobilePhones.put("oppo", new MobilePhone("oppo"));
        mobilePhones.put("xiaomi", new MobilePhone("xiaomi"));
    }

    //静态工厂方法，根据名字获取MobilePhone对象
    public static MobilePhone getMobilePhone(String name) {
        return mobilePhones.get(name);
    }
}
