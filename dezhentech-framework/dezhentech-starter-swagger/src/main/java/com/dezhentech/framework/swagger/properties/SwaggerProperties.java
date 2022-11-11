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
    private String title = "接口文档";

    /**
     * 版本
     */
    private String version = "1.0.0";

    /**
     * 描述
     */
    private String description = "这是项目的接口文档";

    /**
     * 名字
     */
    private String name = "德臻成都健康科技有限公司";

    /**
     * url
     */
    private String url = "https://www.dezhentech.com/";

    /**
     * 电子邮件
     */
    private String email = "service@dezhentech.com";
}
