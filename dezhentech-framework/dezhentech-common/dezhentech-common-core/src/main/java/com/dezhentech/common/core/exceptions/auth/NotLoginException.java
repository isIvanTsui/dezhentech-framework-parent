package com.dezhentech.common.core.exceptions.auth;

/**
 * @description: 未能通过的登录认证异常
 * @title: com.dezhentech.common.core.exceptions.auth.NotLoginException
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 10:39:39
 * @version: 1.0.0
 **/
public class NotLoginException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public NotLoginException(String message)
    {
        super(message);
    }
}
