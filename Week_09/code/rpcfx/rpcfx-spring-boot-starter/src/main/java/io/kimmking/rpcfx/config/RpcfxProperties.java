package io.kimmking.rpcfx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * rpcfx配置
 *
 * @author Liquid
 */
@ConfigurationProperties(prefix = "rpcfx")
@Data
public class RpcfxProperties {
    private Integer port;
    private String zkAddress;
}
