package com.liquid.mq.sample.conf;

import com.liquid.mq.client.consumer.LiquidMqConsumer;
import com.liquid.mq.client.factory.LiquidMqClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

/**
 * 示例配置类
 *
 * @author Liquid
 */
public class LiquidMqConf {
    private final static Logger logger = LoggerFactory.getLogger(LiquidMqConf.class);

    private static class LiquidMqConfHolder {
        private static LiquidMqConf instance = new LiquidMqConf();
    }

    public static LiquidMqConf getInstance() {
        return LiquidMqConfHolder.instance;
    }

    private LiquidMqClientFactory mqClientFactory;

    /**
     * start
     */
    public void start(List<LiquidMqConsumer> consumerList) {
        Properties prop = loadProperties("liquid-mq.properties");
        mqClientFactory = new LiquidMqClientFactory();
        mqClientFactory.setServiceAddr(prop.getProperty("liquid.mq.server.address"));
        mqClientFactory.init();
    }


    public static Properties loadProperties(String propertyFileName) {
        InputStreamReader in = null;
        try {
            ClassLoader loder = Thread.currentThread().getContextClassLoader();

            in = new InputStreamReader(loder.getResourceAsStream(propertyFileName), "UTF-8");
            ;
            if (in != null) {
                Properties prop = new Properties();
                prop.load(in);
                return prop;
            }
        } catch (IOException e) {
            logger.error("load {} error!", propertyFileName);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close {} error!", propertyFileName);
                }
            }
        }
        return null;
    }

    public LiquidMqClientFactory getMqClientFactory() {
        return mqClientFactory;
    }
}
