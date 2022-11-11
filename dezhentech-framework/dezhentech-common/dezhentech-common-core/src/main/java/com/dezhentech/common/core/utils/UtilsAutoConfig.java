package com.dezhentech.common.core.utils;

import com.dezhentech.common.core.utils.spring.DzSpringUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 工具配置类
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.UtilsAutoConfig
 * @since 2022/11/10 20:43:49
 **/
@Configuration
@EnableConfigurationProperties({UtilsProperties.class})
@Import({ DzSpringUtil.class })
public class UtilsAutoConfig {
    private final DzSpringUtil springUtil;
    
    private final UtilsProperties utilsProperties;

    public UtilsAutoConfig(DzSpringUtil springUtil, UtilsProperties utilsProperties) {
        this.springUtil = springUtil;
        this.utilsProperties = utilsProperties;
    }
}
