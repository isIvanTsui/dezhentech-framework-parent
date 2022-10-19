package com.dezhentech.framework.log.annotation;

import java.lang.annotation.*;

/**
 * 自动记录日志注解
 *
 * @author yfcui
 * @date 2022/10/19
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {
    /**
     * 操作信息
     */
    String operation();
}
