package com.dezhentech.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dezhentech.system.entity.SysUserRole;
import com.dezhentech.system.mapper.SysUserRoleMapper;
import com.dezhentech.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @description: 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
 * @title: com.dezhentech.system.service.impl.SysUserRoleServiceImpl
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:53:02
 * @version: 1.0.0
 **/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
        implements SysUserRoleService {

}




