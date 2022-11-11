package com.dezhentech.common.core.exceptions.auth;


/**
 * @description: 没有角色异常
 * @title: com.dezhentech.common.core.exceptions.auth.NotRoleException
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 10:39:46
 * @version: 1.0.0
 **/
public class NotRoleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotRoleException(String role) {
        super(role);
        System.out.println("没有角色权限");
    }

    public NotRoleException(String[] roles) {
        //TODO
        System.out.println("没有角色权限");
//        super(StrUtil.join(roles, ","));
    }
}
