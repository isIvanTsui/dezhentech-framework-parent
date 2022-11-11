package com.dezhentech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dezhentech.system.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Mapper
 * @title: com.dezhentech.system.mapper.SysRoleMenuMapper
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:55:04
 * @version: 1.0.0
 **/
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

}




