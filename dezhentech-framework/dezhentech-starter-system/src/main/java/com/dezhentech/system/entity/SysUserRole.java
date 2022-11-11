package com.dezhentech.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 用户和角色关联表
 * @title: com.dezhentech.system.entity.SysUserRole
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:51:25
 * @version: 1.0.0
 **/
@TableName(value = "sys_user_role")
@Data
public class SysUserRole implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}