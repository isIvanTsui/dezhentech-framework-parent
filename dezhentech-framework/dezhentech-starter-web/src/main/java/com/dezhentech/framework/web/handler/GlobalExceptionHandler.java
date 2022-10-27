package com.dezhentech.framework.web.handler;

import com.dezhentech.framework.common.core.exceptions.ServiceException;
import com.dezhentech.framework.common.core.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @description: 全局异常处理
 * @title: com.dezhentech.framework.web.handler.GlobalExceptionHandler
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/24 09:53:39
 * @version: 1.0.0
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String msg = "发生服务异常";
        if (e instanceof HttpMessageNotReadableException) {
            //基本就是请求参数类型不合法异常
            msg = "发生请求参数异常";
        }
        log.error("RuntimeException 请求地址'{}',{}.", msg, requestURI, e);
        return Result.error(msg);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("Exception 请求地址'{}',发生系统异常.", requestURI, e);
        return Result.error("发生服务异常");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result handleServiceException(ServiceException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("ServiceException 请求地址:'{}', 异常信息:'{}', 详情:{}", requestURI, e.getMessage(), e.getDetail());
        Integer code = e.getCode();
        return null != code ? Result.error(code, e.getMessage()) : Result.error(e.getMessage());
    }

    /**
     * validation参数校验异常 统一处理
     */
    @ExceptionHandler(value = BindException.class)
    public Result handlerBindException(BindException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("ServiceException 请求地址:'{}', 异常信息:'{}'", requestURI, e.getMessage());
        e.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError error : e.getAllErrors()) {
            stringBuilder.append("[");
            stringBuilder.append(((FieldError) error).getField());
            stringBuilder.append(" ");
            stringBuilder.append(error.getDefaultMessage());
            stringBuilder.append("]");
        }
        return Result.error("【参数校验失败】 " + stringBuilder.toString());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result handlerConstraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (ConstraintViolation<?> error : e.getConstraintViolations()) {
            PathImpl pathImpl = (PathImpl) error.getPropertyPath();
            String paramName = pathImpl.getLeafNode().getName();
            stringBuilder.append("[");
            stringBuilder.append(paramName);
            stringBuilder.append(" ");
            stringBuilder.append(error.getMessage());
            stringBuilder.append("]");
        }
        return Result.error("【参数校验失败】 " + stringBuilder.toString());

    }
}