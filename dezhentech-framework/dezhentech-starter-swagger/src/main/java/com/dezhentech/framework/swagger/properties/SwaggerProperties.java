package com.dezhentech.framework.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: swagger属性配置
 * @title: com.dezhentech.framework.swagger.properties.SwaggerProperties
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/25 10:16:23
 * @version: 1.0.0
 **/
@ConfigurationProperties(prefix = "dezhen.swagger")
@Data
public class SwaggerProperties {
    /**
     * 将要扫描的controller所在包
     */
    private String basePackage = "com.dezhentech";

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

    //联系人信息
    /**
     * 名字
     */
    private String name;
    /**
     * url
     */
    private String url;
    /**
     * 电子邮件
     */
    private String email;
}
