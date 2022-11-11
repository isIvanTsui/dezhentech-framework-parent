package com.dezhentech.common.core.response;

import cn.hutool.http.HttpStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * @description: 统一返回数据格式
 * @title: com.dezhentech.common.core.response.Result
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 10:39:59
 * @version: 1.0.0
 **/
@Getter
@Setter(AccessLevel.PRIVATE)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -7921152230789093303L;
    private int code;

    private String msg;

    private T data;

    private Result() {

    }


    /**
     * 私有构造方法，禁止直接创建
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 默认的操作成功
     *
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success() {
        return Result.successMsg("操作成功");
    }


    /**
     * 携带数据的操作成功
     *
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(T data) {
        return Result.success("操作成功", data);
    }


    /**
     * 自定义消息的操作成功
     *
     * @param msg 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> successMsg(String msg) {
        return Result.success(msg, null);
    }


    /**
     * 自定义消息并写到数据的操作成功
     *
     * @param msg  消息
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(String msg, T data) {
        return Result.success(HttpStatus.HTTP_OK, msg, data);
    }


    /**
     * 自定义状态码和消息的操作成功
     *
     * @param code 状态码
     * @param msg  消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(int code, String msg) {
        return Result.success(code, msg, null);
    }


    /**
     * 自定义状态码、消息并携带数据的操作成功
     *
     * @param code 状态码
     * @param msg  消息
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }


    /**
     * 默认的操作失败
     *
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> error() {
        return Result.error("操作失败");
    }


    /**
     * 自定义消息的操作失败
     *
     * @param msg 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> error(String msg) {
        return Result.error(msg, null);
    }


    /**
     * 自定义消息并携带数据的操作失败
     *
     * @param msg  消息
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> error(String msg, T data) {
        return new Result<>(HttpStatus.HTTP_INTERNAL_ERROR, msg, data);
    }


    /**
     * 自定义状态码和消息的操作失败
     *
     * @param code 状态码
     * @param msg  消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

}
