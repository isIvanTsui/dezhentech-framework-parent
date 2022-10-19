package com.dezhentech.framework.log.config;

import com.dezhentech.framework.log.properties.AutoLogProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 自动日志配置
 *
 * @author yfcui
 * @date 2022/10/19
 */
@EnableConfigurationProperties({AutoLogProperties.class})
public class AutoLogConfiguration {

}
