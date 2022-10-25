package com.dezhentech.framework.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.Contact;

/**
 * @description: swagger属性配置
 * @title: com.dezhentech.framework.swagger.properties.SwaggerProperties
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/25 10:16:23
 * @version: 1.0.0
 **/
@ConfigurationProperties(prefix = "dezhentech.swagger")
@Data
public class SwaggerProperties {
    /**
     * 基本包
     */
    private String basePackage;

    /**
     * 标题
     */
    private String title;

    /**
     * 版本
     */
    private String version;

    /**
     * 描述
     */
    private String description;

    /**
     * 联系人信息
     */
    private Contact contact;
}
