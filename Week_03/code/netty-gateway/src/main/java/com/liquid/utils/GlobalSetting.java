package com.liquid.utils;

import cn.hutool.setting.Setting;

import java.util.Map;

/**
 * 全局配置文件
 *
 * @author Liquid
 */
public class GlobalSetting {
    private Setting setting;

    private GlobalSetting() {
        setting = new Setting("service_config.setting");
    }

    /**
     * 静态内部类实现单例
     */
    private static class GlobalSettingHolder {
        private static GlobalSetting INSTANCE = new GlobalSetting();
    }

    public static GlobalSetting getInstance() {
        return GlobalSettingHolder.INSTANCE;
    }

    public Integer getInt(String key) {
        return setting.getInt(key);
    }

    public Map<String, String> getMap(String group) {
        return setting.getMap(group);
    }
}
