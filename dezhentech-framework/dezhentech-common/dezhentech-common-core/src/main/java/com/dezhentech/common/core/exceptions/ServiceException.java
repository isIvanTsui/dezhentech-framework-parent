package com.dezhentech.common.core.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: Service异常
 * @title: com.dezhentech.common.exception.ServiceException
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/24 09:32:18
 * @version: 1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -6309105758613036513L;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误详情
     * <p>
     */
    private String detail;


    /**
     * @param message 异常信息
     */
    public ServiceException(String message) {
        this.message = message;
    }

    /**
     * @param message 异常信息
     * @param code    异常返回前端编号
     */
    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    /**
     * @param message 异常信息
     * @param detail  异常信息
     */
    public ServiceException(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    /**
     * @param message 异常信息
     * @param code    异常返回前端编号
     * @param detail  异常信息
     */
    public ServiceException(String message, Integer code, String detail) {
        this.message = message;
        this.code = code;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceException setDetail(String detail) {
        this.detail = detail;
        return this;
    }
}
