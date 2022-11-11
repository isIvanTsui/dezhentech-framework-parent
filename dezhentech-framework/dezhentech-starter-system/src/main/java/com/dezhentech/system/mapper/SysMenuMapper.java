package com.dezhentech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dezhentech.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
 * @title: com.dezhentech.system.mapper.SysMenuMapper
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:49:21
 * @version: 1.0.0
 **/
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}




