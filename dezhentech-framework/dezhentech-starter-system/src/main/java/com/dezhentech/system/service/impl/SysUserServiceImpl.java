package com.dezhentech.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dezhentech.system.api.entity.SysUser;
import com.dezhentech.system.mapper.SysUserMapper;
import com.dezhentech.system.service.SysUserService;
import org.springframework.stereotype.Service;


/**
 * @description: 针对表【sys_user(用户信息表)】的数据库操作Service
 * @title: com.dezhentech.system.service.impl.SysUserServiceImpl
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:25:22
 * @version: 1.0.0
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

}




