package com.dezhentech.framework.swagger.config;

import com.dezhentech.framework.swagger.properties.SwaggerProperties;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description: knife4j配置
 * @title: com.dezhentech.framework.swagger.config.Knife4jConfig
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/25 10:14:09
 * @version: 1.0.0
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
@EnableConfigurationProperties({SwaggerProperties.class})
public class Knife4jConfig {
    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description(swaggerProperties.getDescription())
                .contact(new Contact(swaggerProperties.getName(), swaggerProperties.getUrl(), swaggerProperties.getEmail()))
                .version(swaggerProperties.getVersion())
                .title(swaggerProperties.getTitle())
                .build();
    }

}