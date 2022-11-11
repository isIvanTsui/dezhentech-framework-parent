package com.dezhentech.auth.service;

import com.dezhentech.common.core.constant.CacheConstants;
import com.dezhentech.common.core.exceptions.ServiceException;
import com.dezhentech.common.security.utils.SecurityUtils;
import com.dezhentech.framework.redis.service.RedisService;
import com.dezhentech.system.api.entity.SysUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @description: 登录密码方法
 * @title: com.dezhentech.auth.service.SysPasswordService
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 11:44:22
 * @version: 1.0.0
 **/
@Component
public class SysPasswordService {
    @Resource
    private RedisService redisService;

    private int maxRetryCount = CacheConstants.PASSWORD_MAX_RETRY_COUNT;

    private Long lockTime = CacheConstants.PASSWORD_LOCK_TIME;


    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUser user, String password) {
        String username = user.getUserName();

        Integer retryCount = redisService.getCacheObject(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= Integer.valueOf(maxRetryCount).intValue()) {
            String errMsg = String.format("密码输入错误%s次，帐户锁定%s分钟", maxRetryCount, lockTime);
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, errMsg);
            throw new ServiceException(errMsg);
        }

        if (!matches(user, password)) {
            retryCount = retryCount + 1;
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, String.format("密码输入错误%s次", retryCount));
            redisService.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new ServiceException("用户不存在/密码错误");
        } else {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUser user, String rawPassword) {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName) {
        if (redisService.hasKey(getCacheKey(loginName))) {
            redisService.deleteObject(getCacheKey(loginName));
        }
    }
}
