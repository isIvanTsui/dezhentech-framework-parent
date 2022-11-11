package com.dezhentech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dezhentech.system.api.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;


/**
 * @description: 针对表【sys_user(用户信息表)】的数据库操作Mapper
 * @title: com.dezhentech.system.mapper.SysUserMapper
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:24:56
 * @version: 1.0.0
 **/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}




