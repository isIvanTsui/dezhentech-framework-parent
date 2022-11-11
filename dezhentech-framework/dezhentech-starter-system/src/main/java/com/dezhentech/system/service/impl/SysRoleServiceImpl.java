package com.dezhentech.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dezhentech.system.api.entity.SysRole;
import com.dezhentech.system.mapper.SysRoleMapper;
import com.dezhentech.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @description: 针对表【sys_role(角色信息表)】的数据库操作Service实现
 * @title: com.dezhentech.system.service.impl.SysRoleServiceImpl
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:46:34
 * @version: 1.0.0
 **/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
        implements SysRoleService {

    @Override
    public List<SysRole> listRolesByUserId(String userId) {
        return null;
    }
}




