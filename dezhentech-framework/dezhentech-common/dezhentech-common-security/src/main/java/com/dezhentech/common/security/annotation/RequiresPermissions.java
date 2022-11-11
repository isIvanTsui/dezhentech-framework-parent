package com.dezhentech.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 权限认证：必须具有指定权限才能进入该方法
 * @title: com.dezhentech.security.annotation.RequiresPermissions
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 02:26:01
 * @version: 1.0.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequiresPermissions {
    /**
     * 需要校验的权限码
     */
    String[] value() default {};

    /**
     * 验证模式：AND | OR，默认AND
     */
    Logical logical() default Logical.AND;
}
