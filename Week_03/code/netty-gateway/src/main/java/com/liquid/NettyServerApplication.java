package com.liquid;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import com.liquid.inbound.HttpInboundServer;

/**
 * Netty Server启动类
 *
 * @author Liquid
 */
public class NettyServerApplication {
    private static Log log = LogFactory.get();
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";

    public static void main(String[] args) {
        //使用配置文件配置端口号信息
        Setting setting = new Setting("service_config.setting");
        Integer portConfig = setting.getInt("server.port");
        int port = ObjectUtil.isEmpty(portConfig) ? 8088 : portConfig;
        log.info(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        HttpInboundServer server = new HttpInboundServer(port);
        log.info(GATEWAY_NAME + " " + GATEWAY_VERSION + " started at http://localhost:{}", port);
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
