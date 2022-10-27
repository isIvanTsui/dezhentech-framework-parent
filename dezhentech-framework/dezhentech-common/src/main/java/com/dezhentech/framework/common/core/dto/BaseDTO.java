package com.dezhentech.framework.common.core.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 基础dto
 *
 * @author yfcui
 * @date 2022/10/19
 */
@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 887776757302265854L;
    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    /**
     * 当前页
     */
    protected Integer pageNum = PAGE_NO;


    /**
     * 每页显示条数
     */
    protected Integer pageSize = PAGE_SIZE;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 更新时间
     */
    protected Date updateTime;
}
