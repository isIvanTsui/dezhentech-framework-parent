package com.dezhentech.auth.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.dezhentech.common.core.constant.SecurityConstants;
import com.dezhentech.common.core.constant.UserConstants;
import com.dezhentech.common.core.enums.UserStatus;
import com.dezhentech.common.core.exceptions.ServiceException;
import com.dezhentech.common.core.response.Result;
import com.dezhentech.common.security.utils.SecurityUtils;
import com.dezhentech.system.api.RemoteUserService;
import com.dezhentech.system.api.entity.SysUser;
import com.dezhentech.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @description: 登录校验方法
 * @title: com.dezhentech.auth.service.SysLoginService
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 11:44:18
 * @version: 1.0.0
 **/
@Component
public class SysLoginService {
    @Autowired(required = false)
    private RemoteUserService remoteUserService;

    @Autowired
    private SysPasswordService passwordService;

    /**
     * 登录
     */
    public LoginUser login(String username, String password) {
        // 用户名或密码为空 错误
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
        // 查询用户信息
        Result<LoginUser> userResult = remoteUserService.getUserInfo(username, SecurityConstants.INNER);

        if (userResult == null || userResult.getData() == null) {
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }

        if (HttpStatus.HTTP_INTERNAL_ERROR == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }

        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        passwordService.validate(user, password);
//        recordLogService.recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    public void logout(String loginName) {
//        recordLogService.recordLogininfor(loginName, Constants.LOGOUT, "退出成功");
    }

    /**
     * 注册
     */
    public void register(String username, String password) {
        // 用户名或密码为空 错误
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }

        // 注册用户信息
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        Result<?> registerResult = remoteUserService.registerUserInfo(sysUser, SecurityConstants.INNER);

        if (HttpStatus.HTTP_INTERNAL_ERROR == registerResult.getCode()) {
            throw new ServiceException(registerResult.getMsg());
        }
//        recordLogService.recordLogininfor(username, Constants.REGISTER, "注册成功");
    }
}
