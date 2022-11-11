package com.dezhentech.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 角色和菜单关联表
 * @title: com.dezhentech.system.entity.SysRoleMenu
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:54:08
 * @version: 1.0.0
 **/
@TableName(value = "sys_role_menu")
@Data
public class SysRoleMenu implements Serializable {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}