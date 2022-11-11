package com.dezhentech.gateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @description: 放行白名单配置
 * @title: com.dezhentech.gateway.config.properties.IgnoreWhiteProperties
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 01:54:49
 * @version: 1.0.0
 **/
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "security.ignore")
@Data
public class IgnoreWhiteProperties {
    /**
     * 放行白名单配置，网关不校验此处的白名单
     */
    private List<String> whites;
}
