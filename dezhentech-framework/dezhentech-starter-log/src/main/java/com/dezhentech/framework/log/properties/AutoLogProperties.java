package com.dezhentech.framework.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dezhentech.auto-log")
public class AutoLogProperties {
    /**
     * 是否开启自动记录日志
     */
    private Boolean enabled = false;
}
