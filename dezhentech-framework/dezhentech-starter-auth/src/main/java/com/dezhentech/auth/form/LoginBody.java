package com.dezhentech.auth.form;

/**
 * @description: 用户登录对象
 * @title: com.dezhentech.auth.form.LoginBody
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 11:44:10
 * @version: 1.0.0
 **/
public class LoginBody
{
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
