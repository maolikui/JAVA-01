package com.liquid.rwseparate.config;

import cn.hutool.core.util.StrUtil;
import com.liquid.rwseparate.dynamic.DynamicDataSource;
import com.liquid.rwseparate.dynamic.DynamicDataSourceContext;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 动态数据源注册
 *
 * @author Liquid
 */
public class DynamicDataSourceRegister
        implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    // 如配置文件中未指定数据源类型，使用该默认值
    private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";

    // 数据源
    private Map<String, DataSource> primaryDataSources = new HashMap<>();
    private Map<String, DataSource> secondaryDataSources = new HashMap<>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.putAll(primaryDataSources);
        for (String key : primaryDataSources.keySet()) {
            DynamicDataSourceContext.primaryDsIds.add(key);
        }

        targetDataSources.putAll(secondaryDataSources);
        for (String key : secondaryDataSources.keySet()) {
            DynamicDataSourceContext.secondaryDsIds.add(key);
        }
        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", primaryDataSources.get(DynamicDataSourceContext.primaryDsIds.get(0)));
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dynamicDbReg", beanDefinition);
    }


    /**
     * 加载多数据源配置
     */
    @Override
    public void setEnvironment(Environment env) {
        initDataSource(env);
    }

    /**
     * 初始化主数据源
     */
    private void initDataSource(Environment env) {
        // 读取主数据源
        Iterable sources = ConfigurationPropertySources.get(env);
        Binder binder = new Binder(sources);
        BindResult bindResult = binder.bind("spring.custom.datasource", Properties.class);
        Properties properties = (Properties) bindResult.get();

        String dsNames = properties.getProperty("names");
        for (String dsName : dsNames.split(",")) {
            Map<String, Object> dsMap = new HashMap<>();
            dsMap.put("driver-class-name", properties.getProperty(dsName + ".driver-class-name"));
            dsMap.put("url", properties.getProperty(dsName + ".jdbc-url"));
            dsMap.put("username", properties.getProperty(dsName + ".username"));
            dsMap.put("password", properties.getProperty(dsName + ".password"));
            dsMap.put("type", properties.getProperty(dsName + ".type"));
            DataSource ds = buildDataSource(dsMap);
            if (StrUtil.contains(dsName, "primary")) {
                primaryDataSources.put(dsName, ds);
            } else {
                secondaryDataSources.put(dsName, ds);
            }
        }
        DynamicDataSourceContext.chooseRouter(properties.getProperty("load-balancer-name"));
    }

    /**
     * 创建DataSource
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        try {
            Object type = dsMap.get("type");
            if (type == null)
                type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource

            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}