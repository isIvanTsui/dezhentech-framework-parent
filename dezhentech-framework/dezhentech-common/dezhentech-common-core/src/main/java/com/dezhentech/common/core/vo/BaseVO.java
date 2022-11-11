package com.dezhentech.common.core.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 基础VO
 * @title: com.dezhentech.common.core.vo.BaseVO
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/04 11:00:37
 * @version: 1.0.0
 **/
@Data
public class BaseVO implements Serializable {
    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;
    private static final long serialVersionUID = 4425230112087886521L;

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
