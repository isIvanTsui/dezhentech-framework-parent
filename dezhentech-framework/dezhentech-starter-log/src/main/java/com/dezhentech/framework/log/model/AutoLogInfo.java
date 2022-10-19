package com.dezhentech.framework.log.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志信息
 *
 * @author yfcui
 * @date 2022/10/19
 */
@Data
public class AutoLogInfo implements Serializable {

    private static final long serialVersionUID = -7461327723774719507L;
    /**
     * 操作时间
     */
    private LocalDateTime timestamp;
    /**
     * 应用名
     */
    private String applicationName;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 操作信息
     */
    private String operation;
}
