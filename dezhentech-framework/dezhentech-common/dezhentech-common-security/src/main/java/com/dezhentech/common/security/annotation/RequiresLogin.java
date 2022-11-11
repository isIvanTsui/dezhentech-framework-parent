package com.dezhentech.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 登录认证：只有登录之后才能进入该方法
 * @title: security.annotation.RequiresLogin
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 11:09:25
 * @version: 1.0.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RequiresLogin
{
}
