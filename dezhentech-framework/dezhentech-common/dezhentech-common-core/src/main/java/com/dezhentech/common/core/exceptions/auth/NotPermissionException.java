package com.dezhentech.common.core.exceptions.auth;

/**
 * @description: 未能通过的权限认证异常
 * @title: com.dezhentech.common.core.exceptions.auth.NotPermissionException
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 10:39:43
 * @version: 1.0.0
 **/
public class NotPermissionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotPermissionException(String permission) {
        super(permission);
    }

    public NotPermissionException(String[] permissions) {
        //TODO
//        super(StringUtils.join(permissions, ","));
    }
}
