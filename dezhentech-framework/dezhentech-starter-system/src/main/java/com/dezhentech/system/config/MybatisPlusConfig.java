package com.dezhentech.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description: mybatis +配置
 * @title: com.dezhentech.system.config.MybatisPlusConfig
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 02:55:07
 * @version: 1.0.0
 **/
@Configuration
@MapperScan("com.dezhentech.system.mapper")
public class MybatisPlusConfig {
}
