package com.dezhentech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dezhentech.system.api.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;


/**
 * @description: 针对表【sys_role(角色信息表)】的数据库操作Mapper
 * @title: com.dezhentech.system.mapper.SysRoleMapper
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:46:13
 * @version: 1.0.0
 **/
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

}




