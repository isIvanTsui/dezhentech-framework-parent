package com.dezhentech.system.api;

import com.dezhentech.common.core.constant.SecurityConstants;
import com.dezhentech.common.core.constant.ServiceNameConstants;
import com.dezhentech.common.core.response.Result;
import com.dezhentech.system.api.entity.SysUser;
import com.dezhentech.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteUserService {
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/user/info_username")
    Result<LoginUser> getUserInfo(@RequestParam("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @param source  请求来源
     * @return 结果
     */
    @PostMapping("/user/register")
    public Result<Boolean> registerUserInfo(@RequestBody SysUser sysUser, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
