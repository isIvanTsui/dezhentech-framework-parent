package com.dezhentech.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.dezhentech.auth.form.LoginBody;
import com.dezhentech.auth.form.RegisterBody;
import com.dezhentech.auth.service.SysLoginService;
import com.dezhentech.common.core.response.Result;
import com.dezhentech.common.core.utils.JwtUtils;
import com.dezhentech.common.security.auth.AuthUtil;
import com.dezhentech.common.security.service.TokenService;
import com.dezhentech.common.security.utils.SecurityUtils;
import com.dezhentech.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: token 控制
 * @title: com.dezhentech.auth.controller.TokenController
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 11:44:00
 * @version: 1.0.0
 **/
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("login")
    public Result<?> login(@RequestBody LoginBody form) {
        // 用户登录
        LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return Result.success(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public Result<?> logout(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StrUtil.isNotBlank(token)) {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return Result.success();
    }

    @PostMapping("refresh")
    public Result<?> refresh(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser != null) {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return Result.success();
        }
        return Result.success();
    }

    @PostMapping("register")
    public Result<?> register(@RequestBody RegisterBody registerBody) {
        // 用户注册
        sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        return Result.success();
    }
}
