package com.dezhentech.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @description: 角色认证：必须具有指定角色标识才能进入该方法
 * @title: com.dezhentech.security.annotation.RequiresRoles
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 02:26:16
 * @version: 1.0.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequiresRoles {
    /**
     * 需要校验的角色标识
     */
    String[] value() default {};

    /**
     * 验证逻辑：AND | OR，默认AND
     */
    Logical logical() default Logical.AND;
}
