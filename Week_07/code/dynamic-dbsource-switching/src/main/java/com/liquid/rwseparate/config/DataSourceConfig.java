package com.liquid.rwseparate.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.liquid.rwseparate.dynamic.DynamicDataSource;
import com.liquid.rwseparate.dynamic.DynamicDataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


/**
 * 主从配置
 *
 * @author Liquid
 */
@Configuration
//@Import(DynamicDataSourceRegister.class)
@MapperScan(basePackages = "com.liquid.rwseparate.db.dao", sqlSessionTemplateRef = "sqlTemplate")
public class DataSourceConfig {
    /**
     * 配置主库
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.primary-datasource")
    public DataSource primaryDb() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置从库
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.secondary-datasource")
    public DataSource secondaryDb() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 主从动态配置
     */
    @Bean
    public DynamicDataSource dynamicDb(@Qualifier("primaryDb") DataSource primaryDataSource,
                                       @Autowired(required = false) @Qualifier("secondaryDb") DataSource secondaryDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceEnum.PRIMARY.getDataSourceName(), primaryDataSource);
        if (secondaryDataSource != null) {
            targetDataSources.put(DynamicDataSourceEnum.SECONDARY.getDataSourceName(), secondaryDataSource);
        }
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(primaryDataSource);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDb") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:com/liquid/rwseparate/db/dao/*Mapper.xml"));
        bean.setDataSource(dynamicDataSource);
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlTemplate(@Qualifier("sessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "dataSourceTx")
    public DataSourceTransactionManager dataSourceTx(@Qualifier("dynamicDb") DataSource dynamicDataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource);
        return dataSourceTransactionManager;
    }
}
