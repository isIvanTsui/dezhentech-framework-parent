package com.dezhentech.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dezhentech.common.core.constant.SecurityConstants;
import com.dezhentech.common.core.response.Result;
import com.dezhentech.system.api.RemoteUserService;
import com.dezhentech.system.api.entity.SysUser;
import com.dezhentech.system.api.model.LoginUser;
import com.dezhentech.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @description: 用户信息Controller
 * @title: com.dezhentech.system.controller.SysUserController
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:28:00
 * @version: 1.0.0
 **/
@RestController
@RequiredArgsConstructor
public class SysUserController implements RemoteUserService {
    private final SysUserService userService;

    @Override
    public Result<LoginUser> getUserInfo(@RequestParam("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        List<SysUser> list = userService.list(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, username));
        if (CollectionUtil.isNotEmpty(list)) {
            SysUser user = list.get(0);
            LoginUser loginUser = new LoginUser();
            loginUser.setSysUser(user);
            //TODO 角色、权限还未查询出来
            return Result.success(loginUser);
        }
        return null;
    }

    @Override
    public Result<Boolean> registerUserInfo(SysUser sysUser, String source) {
        return null;
    }
}
