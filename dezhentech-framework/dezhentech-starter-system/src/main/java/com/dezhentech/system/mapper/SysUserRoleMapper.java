package com.dezhentech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dezhentech.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
 * @title: com.dezhentech.system.mapper.SysUserRoleMapper
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:52:22
 * @version: 1.0.0
 **/
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}




