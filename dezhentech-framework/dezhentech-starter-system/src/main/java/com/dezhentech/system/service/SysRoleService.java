package com.dezhentech.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dezhentech.system.api.entity.SysRole;

import java.util.List;


/**
 * @description: 针对表【sys_role(角色信息表)】的数据库操作Service
 * @title: com.dezhentech.system.service.SysRoleService
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:45:43
 * @version: 1.0.0
 **/
public interface SysRoleService extends IService<SysRole> {
    List<SysRole> listRolesByUserId(String userId);
}
