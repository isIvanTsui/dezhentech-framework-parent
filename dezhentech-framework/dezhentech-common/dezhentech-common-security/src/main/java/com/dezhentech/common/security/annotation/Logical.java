package com.dezhentech.common.security.annotation;

/**
 * @description: 权限注解的验证模式
 * @title: com.dezhentech.security.annotation.Logical
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 02:24:54
 * @version: 1.0.0
 **/
public enum Logical {
    /**
     * 必须具有所有的元素
     */
    AND,

    /**
     * 只需具有其中一个元素
     */
    OR
}
